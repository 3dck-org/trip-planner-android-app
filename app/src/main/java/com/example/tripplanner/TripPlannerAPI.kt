package com.example.tripplanner

import com.example.tripplanner.domain.*
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface TripPlannerAPI {

    @POST("api/v1/users")
    fun registerAsync(@Body registrationRequest: RegistrationRequest): Deferred<Response<OauthResponse>>

    @POST("oauth/token")
    fun loginAsync(@Body registrationRequest: LoginRequest): Deferred<Response<OauthResponse>>

    @POST("oauth/token")
    suspend fun getRefreshTokenAsync(@Body registrationRequest: LoginRequest): OauthResponse

    @GET("api/v1/trips")
    fun getTripsAsync(@HeaderMap mapOfHeaders: Map<String, String>): Deferred<Response<TripsResponse>>

    @GET("api/v1/journeys")
    fun getUsersTripsAsync(@HeaderMap mapOfHeaders: Map<String, String>): Deferred<Response<JourneysResponse>>

    @POST("api/v1/journeys")
    fun subscribeOnTripAsync(
        @HeaderMap mapOfHeaders: Map<String, String>, @Body subscribeOnTripRequest:
        SubscribeOnTripRequest
    ): Deferred<Response<SubscribeOnTripResponse>>

    @GET("api/v1/current_user")
    fun getUsersDetailsAsync(@HeaderMap mapOfHeaders: Map<String, String>): Deferred<Response<UserDetails>>

    @GET("api/v1/current_journey")
    fun getCurrentJourneyAsync(@HeaderMap mapOfHeaders: Map<String, String>): Deferred<Response<CurrentJourneyResponse>>

    @GET("api/v1/trips/{id}")
    fun getTripByIdAsync(@HeaderMap mapOfHeaders: Map<String, String>, @Path("id") tripId: Int) : Deferred<Response<TripByIdResponse>>

    @PUT("/api/v1/trips/{id}")
    fun modifyTripToFavouritesAsync(
        @HeaderMap mapOfHeaders: Map<String, String>,
        @Path("id") tripId: Int,
        @Body trip: Trips
    ): Deferred<Response<TripsResponseItem>>

    @PUT("/api/v1/trips/{id}")
    fun modifyLikeAsync(
        @HeaderMap mapOfHeaders: Map<String, String>,
        @Path("id") tripId: Int,
        @Body isLiked: TripLikeRequest
    ) : Deferred<Response<TripsResponseItem>>
}
