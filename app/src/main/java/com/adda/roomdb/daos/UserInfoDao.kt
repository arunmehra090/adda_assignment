package com.adda.roomdb.daos

import androidx.room.*
import com.adda.roomdb.entities.UserInfoModel

@Dao
interface UserInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserInfo(list: List<UserInfoModel>)

    @Query("DELETE from userinfo")
    suspend fun deleteAllUserInfo()

    @Query("SELECT * from userinfo")
    suspend fun getAllUserInfo() : List<UserInfoModel>
}