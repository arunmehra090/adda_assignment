package com.adda.base

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.adda.remote.RetrofitBuilder
import com.adda.roomdb.AppDatabase
import com.adda.userInfo.repositories.MainViewModelFactory
import com.adda.userInfo.repositories.UserInfoRepository

object Injection {
    private fun provideUserInfoRepository(context: Context): UserInfoRepository {
        return UserInfoRepository(
            RetrofitBuilder.injectApiService(),
            AppDatabase.getInstance(context)
        )
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return MainViewModelFactory(provideUserInfoRepository(context))
    }
}