package com.example.tripplanner.repositories

import com.example.tripplanner.TripPlannerAPI
import com.example.tripplanner.constants.Constants
import com.example.tripplanner.extensions.ExternalUserData
import com.example.tripplanner.models.Error
import com.example.tripplanner.models.ErrorData
import com.example.tripplanner.models.OauthResponse
import com.example.tripplanner.models.Resource
import com.example.tripplanner.sharedpreferences.EncryptedSharedPreferences
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

open class BaseRepository {

    @Inject
    lateinit var apiForToken: TripPlannerAPI

    @Inject
    lateinit var sharedPreferences: EncryptedSharedPreferences

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

    open suspend fun <T> callOrError(func: Deferred<Response<T>>): Flow<Resource<T>> = flow {
        emit(Resource.Progress())
        try {
            func.await().let { resp ->
                resp.body().let { body ->
                    if (body != null) {
                        emit(Resource.Success<T>(body))
                    } else {
                        if (resp.code() == 401) {
                            callForToken(
                                apiForToken.login(ExternalUserData(sharedPreferences).getExternalUserData()),
                                func
                            )
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
                            e.localizedMessage ?: "",
                            "unknown",
                            "unknown",
                        ),
                        listOf()
                    )
                )
            )
            e.printStackTrace()
        }
    }


    open suspend fun <L> callForToken(
        func: Deferred<Response<OauthResponse>>,
        func2: Deferred<Response<L>>
    ): Flow<Resource<OauthResponse>> = flow {
        emit(Resource.Progress())
        Timber.d("!@$(*$!@(")
        try {
            func.await().let { resp ->
                resp.body().let { body ->
                    if (body != null) {
                        sharedPreferences.addPreference(Constants.TOKEN, body.access_token)
                        sharedPreferences.addPreference(Constants.REFRESH_TOKEN, body.refresh_token)
                        addToken(body.access_token)
                        refreshToken(body.refresh_token)
                        callOrError(func2)
                    } else {
                        resp.errorBody()?.let {
                            emit(
                                Resource.Error<OauthResponse>(
                                    Gson().fromJson(
                                        it.string(),
                                        ErrorData::class.java
                                    )
                                )
                            )
                            val error = Gson().fromJson(
                                it.string(),
                                ErrorData::class.java
                            )

                        }
                    }

                }
            }
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    ErrorData(
                        Error(
                            1,
                            e.message.toString(),
                            e.localizedMessage ?: "",
                            "unknown",
                            "unknown",
                        ),
                        listOf()
                    )
                )
            )

            e.printStackTrace()
        }
    }
}