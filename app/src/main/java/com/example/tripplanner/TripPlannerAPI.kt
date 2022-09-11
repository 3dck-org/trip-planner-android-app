package com.example.tripplanner

import com.example.tripplanner.models.LoginRequest
import com.example.tripplanner.models.RegistrationRequest
import com.example.tripplanner.models.OauthResponse
import com.example.tripplanner.models.TripsResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface TripPlannerAPI {

    @POST("api/v1/users")
    fun register(@Body registationRequest : RegistrationRequest): Deferred<Response<OauthResponse>>

    @POST("oauth/token")
    fun login(@Body registationRequest : LoginRequest): Deferred<Response<OauthResponse>>

    @GET("api/v1/trips")
    fun getTrips(@HeaderMap mapOfHeaders: Map<String, String>): Deferred<Response<TripsResponse>>

}
