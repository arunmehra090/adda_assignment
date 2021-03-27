package com.adda.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adda.roomdb.daos.UserInfoDao
import com.adda.roomdb.entities.UserInfoModel

@Database(version = 1, entities = [UserInfoModel::class], exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userInfoDao(): UserInfoDao

    companion object {
        private const val USER_INFO_DB = "userInfo.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, USER_INFO_DB)
                .build()

        fun destroyInstance() {
            INSTANCE = null
        }
    }


}
