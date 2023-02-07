package com.example.tripplanner.repositories

import com.example.tripplanner.TripPlannerAPI
import com.example.tripplanner.constants.Constants
import com.example.tripplanner.domain.*
import com.example.tripplanner.utils.sharedpreferences.EncryptedSharedPreferences
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

open class BaseRepository {

    @Inject
    lateinit var tripApi: TripPlannerAPI

    @Inject
    lateinit var sharedPref: EncryptedSharedPreferences

    private val _response =
        MutableStateFlow<Resource<OauthResponse>>(Resource.Progress<OauthResponse>())
    val response: StateFlow<Resource<OauthResponse>>
        get() = _response

    companion object {
        val mapOfHeaders = mutableMapOf(
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
                        emit(Resource.Success(body))
                    } else {
                        when (resp.code()) {
                            401 -> {
                                sharedPref.sharedPreferences.all.let {
                                    flow {
                                        val ouath = tripApi.getRefreshTokenAsync(
                                            LoginRequest(
                                                client_secret = Constants.SECRET,
                                                client_id = Constants.CLIENT_ID,
                                                refresh_token = it[Constants.REFRESH_TOKEN] as String,
                                                grant_type = "refresh_token",
                                                email = null,
                                                password = null
                                            )
                                        )
                                        emit(ouath)
                                    }.collect { newToken ->
                                        with(sharedPref) {
                                            addPreference(
                                                Constants.REFRESH_TOKEN,
                                                newToken.refresh_token
                                            )
                                            addPreference(
                                                Constants.TOKEN,
                                                newToken.access_token
                                            )
                                        }
                                        addToken(newToken.access_token)
                                        callOrError(funs)
                                    }
                                }
                            }
                            200 -> {
                                emit(Resource.Empty())
                            }
                            else -> {
                                resp.errorBody()?.let {
                                    emit(
                                        Resource.Error(
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
            }
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    ErrorData(
                        303,
                        listOf("Unknown exception")
                    )
                )
            )
            e.printStackTrace()
        }
    }

}