package com.adda.userInfo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adda.models.ResultState
import com.adda.userInfo.repositories.UserInfoRepository

class UserInfoViewModel(private val userInfoRepository: UserInfoRepository)
    : ViewModel() {

    fun getUserInfoLiveData(): LiveData<ResultState> = userInfoRepository.getUserInfoLiveData()

    fun fetchUserDetailsService() { userInfoRepository.fetchUserInfoDetails() }

    override fun onCleared() {
        super.onCleared()
        userInfoRepository.cancelAllOperation()
    }
}

