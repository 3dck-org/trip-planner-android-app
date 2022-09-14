package com.example.tripplanner

import com.example.tripplanner.models.*
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

    @POST("api/v1/journeys")
    fun subscribeOnTrip(@HeaderMap mapOfHeaders: Map<String, String>, @Body subscribeOnTripRequest:
        SubscribeOnTripRequest) : Deferred<Response<SubscribeOnTripResponse>>
}
