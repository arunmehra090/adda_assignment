package com.adda.models

import com.adda.roomdb.entities.UserInfoModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserInfoResponseModel(
        @SerializedName("code")
        @Expose
        var code: Int = 0,
        @SerializedName("meta")
        @Expose
        var metData: Meta? = null,
        @SerializedName("data")
        @Expose
        var data: ArrayList<UserInfoModel> = arrayListOf()
) {
        fun isStatusOK(): Boolean = code == 200

        fun isSuccessFul(): Boolean = isStatusOK()
}

data class Meta(
        @SerializedName("pagination")
        @Expose
        var pagination: Pagination
)

data class Pagination(
        @SerializedName("total")
        @Expose
        var total: Long = 0L,
        @SerializedName("pages")
        @Expose
        var pages: Int = 0,
        @SerializedName("page")
        @Expose
        var page: Int = 0,
        @SerializedName("limit")
        @Expose
        var limit: Int = 0
)


