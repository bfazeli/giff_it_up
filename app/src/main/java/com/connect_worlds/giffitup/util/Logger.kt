package com.connect_worlds.giffitup.util

import android.util.Log
import com.connect_worlds.giffitup.util.Constants.DEBUG
import com.connect_worlds.giffitup.util.Constants.TAG

var isUnitTest = false

fun printLogD(className: String?, message: String) {
    if (DEBUG && !isUnitTest) {
        Log.d(TAG, "$className: $message")
    } else if (DEBUG && isUnitTest) {
        println("$className: $message")
    }
}