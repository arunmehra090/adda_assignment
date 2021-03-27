package com.adda.userInfo.repositories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adda.userInfo.viewmodels.UserInfoViewModel

class MainViewModelFactory(private val userInfoRepository: UserInfoRepository)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserInfoViewModel::class.java)) {
            return UserInfoViewModel(userInfoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}