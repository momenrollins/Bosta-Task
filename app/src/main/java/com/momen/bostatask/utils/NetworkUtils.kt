package com.momen.bostatask.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

object NetworkUtils {
    suspend fun hasInternetConnection(): Boolean {
        return try {
            val result = withContext(Dispatchers.IO) {
                val command = "ping -c 1 google.com"
                Runtime.getRuntime().exec(command).waitFor()
            }
            result == 0
        } catch (e: IOException) {
            false
        }
    }
}