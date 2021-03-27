package com.adda.userInfo.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adda.models.ResultState
import com.adda.models.UserInfoResponseModel
import com.adda.remote.ApiService
import com.adda.roomdb.AppDatabase
import com.adda.utils.ConnectionDetector
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

/**
 * Repository class that works with local and remote data sources.
 */
class UserInfoRepository(
        private val apiService: ApiService,
        private val appDatabase: AppDatabase
) : CoroutineScope {

    private val job = SupervisorJob()

    private val exceptionHandler: CoroutineContext = CoroutineExceptionHandler { _, throwable ->
        Log.d(Log.ERROR.toString(), throwable.message ?: "some error occurred")
        cancelJob()
    }
    override val coroutineContext: CoroutineContext = Dispatchers.IO + job + exceptionHandler

    private var isServiceExecuted = false
    private val userInfoLiveData = MutableLiveData<ResultState>()

    fun getUserInfoLiveData(): LiveData<ResultState> = userInfoLiveData

    init {
        isServiceExecuted = true
    }

    fun fetchUserInfoDetails() {

        fetchUserInfoDetailsFromLocalDb()

        if (ConnectionDetector.isConnectingToInternet()) {
            apiService.getUserInfo().enqueue(object : Callback<UserInfoResponseModel> {
                override fun onResponse(call: Call<UserInfoResponseModel>, response: Response<UserInfoResponseModel>) {
                    try {
                        response.apply {
                            if (isSuccessful && code() == 200 && body() is UserInfoResponseModel) {
                                (body() as UserInfoResponseModel).apply {
                                    if (data.isNotEmpty() && isServiceExecuted) {
                                        userInfoLiveData.postValue(ResultState.Success(data))

                                        // insert data into database
                                        launch {
                                            withContext(Dispatchers.IO) {
                                                appDatabase.userInfoDao().insertUserInfo(data)
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (isServiceExecuted) {
                                    userInfoLiveData.postValue(ResultState.Error("Some error occurred"))
                                }
                            }
                        }
                    } catch (e: Exception) {
                        cancel(CancellationException(e.message))
                        if (isServiceExecuted) {
                            userInfoLiveData.postValue(ResultState.Error("Some error occurred"))
                        }
                    }
                }

                override fun onFailure(call: Call<UserInfoResponseModel>, t: Throwable) {
                    cancel(CancellationException(t.message))
                    if (isServiceExecuted) {
                        userInfoLiveData.postValue(ResultState.Error(t.message ?: "Some error occurred"))
                    }
                }
            })
        }
    }

    // fetch user info data from local database
    private fun fetchUserInfoDetailsFromLocalDb() {
        launch {
            try {
                appDatabase.userInfoDao().getAllUserInfo().let {
                    userInfoLiveData.postValue(ResultState.Success(it))
                }
            } catch (e: Exception) {
                cancel(CancellationException(e.message))
            }
        }
    }

    private fun cancelJob() {
        job.invokeOnCompletion {
            if (job.isCompleted) {
                exceptionHandler.cancelChildren()
            }

            if (job.isCancelled) {
                exceptionHandler.cancelChildren(CancellationException(it?.message))
            }
        }
    }

    fun cancelAllOperation() {
        isServiceExecuted = false
        cancelJob()
    }
}