package com.healthcard.util

sealed class ResponseResult<out T>{
    data class Success<out T>(val data: T): ResponseResult<T>()
    data class Error(val message: String): ResponseResult<Nothing>()
    data object Loading: ResponseResult<Nothing>()
    data object None: ResponseResult<Nothing>()
}
