package com.example.vn008xw.carbeat.data.vo

import android.support.annotation.Nullable
import android.util.ArrayMap
import android.util.Log
import retrofit2.Response
import java.io.IOException
import java.util.*
import java.util.regex.Pattern


/**
 * Common class used by API responses
 */

class ApiResponse<T> {
    val code: Int
    @Nullable
    val body: T?
    @Nullable
    val errorMessage: String?

    constructor(error: Throwable) {
        code = 500
        body = null
        errorMessage = error.message
    }

    constructor(response: Response<T>) {
        code = response.code()
        Log.d("APIResponse", "The code was: " + code);
        if (isSuccessful()) {
            body = response.body()
            errorMessage = null
        } else {
            var message: String? = null
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string()
                } catch (ignored: IOException) {
                    Log.e("ApiResponse", "error while parsing response: " + ignored)
                }

            }
            if (message == null || message.trim { it <= ' ' }.length == 0) {
                message = response.message()
            }
            errorMessage = message
            body = null
        }
    }

    fun isSuccessful(): Boolean = when(code) {
        in 200..300 -> true
        else -> false
    }
}