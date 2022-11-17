package com.example.omdbapi.utils

import android.util.Log

object ApiLogger {
    fun isSuccess(useCaseName: String, result: Any){
        Log.i(useCaseName,"Result: $result")
    }

    fun isUnSuccess(useCaseName: String, message:String){
        Log.w(useCaseName,"Message: $message")
    }

    fun isFailure(useCaseName: String, message: String){
        Log.e(useCaseName,"Message: $message")
    }
}