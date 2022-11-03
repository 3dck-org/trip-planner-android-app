package com.example.tripplanner.repositories

import com.example.tripplanner.models.Error
import com.example.tripplanner.models.ErrorData
import com.example.tripplanner.models.Resource
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
        )

        fun addToken(token: String) {
            mapOfHeaders["Authorization"] = "Bearer $token"
        }

        fun refreshToken(refreshToken: String) {
            mapOfHeaders.remove("Authorization")
            mapOfHeaders["Authorization"] = "Bearer $refreshToken"
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
                        if (resp.code() == 401) {
                            callOrError(funs)
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