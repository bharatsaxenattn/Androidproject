package com.example.album.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.isNetworkAvailable(): Boolean {
    val connect = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connect.activeNetworkInfo != null && connect.activeNetworkInfo!!.isConnected
}