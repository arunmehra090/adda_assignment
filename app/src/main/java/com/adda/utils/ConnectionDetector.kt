package com.adda.utils

import android.content.Context
import android.net.ConnectivityManager
import com.adda.base.MyApplication
import java.lang.ref.WeakReference

object ConnectionDetector {
    /**
     * Checking for all possible internet providers
     */
    fun isConnectingToInternet(): Boolean {
        return WeakReference<Context>(MyApplication.application).get()?.run {
            (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo?.isConnected ?: false
        }?: false
    }
}