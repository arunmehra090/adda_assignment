package com.adda.userInfo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adda.models.ResultState
import com.adda.userInfo.repositories.UserInfoRepository
class UserInfoViewModel(private val userInfoRepository: UserInfoRepository)
    : ViewModel() {

    lateinit var mResponseLiveData: LiveData<ResultState>

    fun fetchUserDetailsService() {
        mResponseLiveData = userInfoRepository.fetchUserInfoDetailsFromLocalDb()
        mResponseLiveData = userInfoRepository.fetchUserInfoDetails()
    }

    override fun onCleared() {
        super.onCleared()
        userInfoRepository.cancelAllOperation()
    }
}


