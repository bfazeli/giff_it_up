package com.connect_worlds.giffitup.business.data.util

import com.connect_worlds.giffitup.business.data.cache.CacheConstants.CACHE_TIMEOUT
import com.connect_worlds.giffitup.business.data.cache.CacheErrors.CACHE_ERROR_TIMEOUT
import com.connect_worlds.giffitup.business.data.cache.CacheErrors.CACHE_ERROR_UNKNOWN
import com.connect_worlds.giffitup.business.data.cache.CacheResult
import com.connect_worlds.giffitup.business.data.network.ApiResult
import com.connect_worlds.giffitup.business.data.network.NetworkConstants.NETWORK_TIMEOUT
import com.connect_worlds.giffitup.business.data.network.NetworkErrors.NETWORK_ERROR_TIMEOUT
import com.connect_worlds.giffitup.business.data.network.NetworkErrors.NETWORK_ERROR_UNKNOWN
import com.connect_worlds.giffitup.business.data.util.GenericErrors.ERROR_UNKNOWN
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T?
): ApiResult<T?> =
    withContext(dispatcher) {
        try {
            withTimeout(NETWORK_TIMEOUT) {
                ApiResult.Success(apiCall.invoke())
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            when (throwable) {
                is TimeoutCancellationException -> {
                    val code = 408
                    ApiResult.GenericError(code, NETWORK_ERROR_TIMEOUT)
                }
                is IOException -> {
                    ApiResult.NetworkError
                }
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ApiResult.GenericError(
                        code,
                        errorResponse
                    )
                }
                else -> {
                    ApiResult.GenericError(
                        null,
                        NETWORK_ERROR_UNKNOWN
                    )
                }
            }
        }
    }

suspend fun <T> safeCacheCall(
    dispatcher: CoroutineDispatcher,
    cacheCall: suspend () -> T?
): CacheResult<T?> =
    withContext(dispatcher) {
        try {
            withTimeout(CACHE_TIMEOUT) {
                CacheResult.Success(cacheCall.invoke())
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            when (throwable) {
                is TimeoutCancellationException -> {
                    CacheResult.GenericError(CACHE_ERROR_TIMEOUT)
                }
                else -> {
                    CacheResult.GenericError(CACHE_ERROR_UNKNOWN)
                }
            }
        }
    }


private fun convertErrorBody(throwable: HttpException): String? =
    try {
        throwable.response()?.errorBody()?.string()
    } catch (exception: Exception) {
        ERROR_UNKNOWN
    }