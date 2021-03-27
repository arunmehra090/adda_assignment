package com.adda.userInfo.repositories

import android.util.Log
import androidx.lifecycle.liveData
import com.adda.models.ResultState
import com.adda.remote.ApiService
import com.adda.roomdb.AppDatabase
import com.adda.roomdb.entities.UserInfoModel
import kotlinx.coroutines.*
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

    fun fetchUserInfoDetails() = liveData {
        val apiResponse = apiService.getUserInfoByLiveData()
        try {
            apiResponse.body()?.data?.let {
                emit(ResultState.Success(it))
                insertUserInfoIntoLocalDb(it)
            } ?: emit(ResultState.Error("Some error occurred"))
        } catch (e: Exception) {
            emit(ResultState.Error("Some error occurred"))
            cancel(CancellationException(e.message))
        }
    }

    // fetch user info data from local database
    fun fetchUserInfoDetailsFromLocalDb() = liveData(Dispatchers.IO) {
        try {
            appDatabase.userInfoDao().getAllUserInfo().let {
                emit(ResultState.Success(it))
            }
        } catch (e: Exception) {
            cancel(CancellationException(e.message))
            emit(ResultState.Error(e.message ?: "Some error occurred"))
        }
    }

    private fun insertUserInfoIntoLocalDb(list: ArrayList<UserInfoModel>) {
        // insert data into database
        launch {
            withContext(Dispatchers.IO) {
                appDatabase.userInfoDao().insertUserInfo(list)
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
        cancelJob()
    }
}