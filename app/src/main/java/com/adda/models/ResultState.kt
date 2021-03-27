package com.adda.models

import com.adda.roomdb.entities.UserInfoModel

sealed class ResultState {
    data class Success(val data: List<UserInfoModel>) : ResultState()
    data class Error(val errorMessage: String) : ResultState()
}
