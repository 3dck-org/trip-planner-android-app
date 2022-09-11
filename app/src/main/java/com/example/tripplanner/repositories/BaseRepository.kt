package com.example.tripplanner.repositories

import com.example.tripplanner.models.*
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

open class BaseRepository {

    companion object {
        val mapOfHeaders = mutableMapOf<String, String>(
            Pair("X-SK-API-KEY", "test"),
            Pair("X-SK-API-SECRET", "1234"),
            Pair("Accept-Language", "en"),
            Pair(
                "user-agent",
                "SafeKiddo Parental Control(1.1.0-dev; Android 11; https://dev-2-api.safekiddo.net/);"
            )
        )

        fun addToken(token: String) {
            mapOfHeaders["Authorization"] = "Bearer $token"
        }

        fun refreshToken(refreshToken: String) {
            mapOfHeaders["Authorization"] = "Bearer $refreshToken"
        }

        fun removeToken() {
            mapOfHeaders.remove("Authorization")
        }
    }

    open suspend fun <T> callOrError(funs: Deferred<Response<T>>): Flow<Resource<T>> = flow {
        emit(Resource.Progress())
        try {
            funs.await().let { resp ->
                resp.body().let { body ->
                    if (body != null) {
                        emit(Resource.Success<T>(body))
                    } else {
                        resp.errorBody()?.let {
                            emit(
                                Resource.Error<T>(
                                    Gson().fromJson(
                                        it.string(),
                                        ErrorData::class.java
                                    )
                                )
                            )
                        }
                    }

                }
            }
        } catch (e: Exception) {
            emit(
                Resource.Error<T>(
                    ErrorData(
                        Error(
                            1,
                            e.message.toString(),
                            e.localizedMessage,
                            "unknown",
                            "unknown"
                        )
                    )
                )
            )
            e.printStackTrace()
        }
    }

}