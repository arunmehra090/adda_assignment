package com.adda.roomdb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "UserInfo")
data class UserInfoModel(
    @PrimaryKey @SerializedName("id")
    @Expose
    var userId: String = "",
    @SerializedName("name")
    @Expose
    var userName: String = "",
    @SerializedName("email")
    @Expose
    var userEmail: String = "",
    @SerializedName("gender")
    @Expose
    var userGender: String = "",
    @SerializedName("status")
    @Expose
    var userStatus: String = "",
    @SerializedName("created_at")
    @Expose
    var userCreatedAt: String = "",
    @SerializedName("updated_at")
    @Expose
    var userUpdatedAt: String = ""
)