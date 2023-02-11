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
    fun getTripsAsync(
        @HeaderMap mapOfHeaders: Map<String, String>
    ): Deferred<Response<TripsResponse>>

    @GET("api/v1/trips")
    fun getTrips(
        @HeaderMap mapOfHeaders: Map<String, String>,
        @Query("city") city: String? = null,
        @Query("category_names") category: String? = null
    ): Deferred<Response<TripsResponse>>

    @GET("api/v1/journeys")
    fun getUsersTripsAsync(@HeaderMap mapOfHeaders: Map<String, String>): Deferred<Response<JourneysResponse>>

    @POST("api/v1/journeys")
    fun subscribeOnTripAsync(
        @HeaderMap mapOfHeaders: Map<String, String>, @Body subscribeOnTripRequest:
        SubscribeOnTripRequest
    ): Deferred<Response<SubscribeOnTripResponse>>

    @PUT("api/v1/journeys/{id}")
    fun unsubscribeOnTripAsync(
        @HeaderMap mapOfHeaders: Map<String, String>,
        @Body subscribeOnTripRequest: UnsubscribeOnTripRequest,
        @Path("id") journeyId: Int
    ): Deferred<Response<SubscribeOnTripResponse>>

    @GET("api/v1/current_user")
    fun getUsersDetailsAsync(@HeaderMap mapOfHeaders: Map<String, String>): Deferred<Response<UserDetails>>

    @GET("api/v1/current_journey")
    fun getCurrentJourneyAsync(@HeaderMap mapOfHeaders: Map<String, String>): Deferred<Response<CurrentJourneyResponse>>

    @GET("api/v1/trips/{id}")
    fun getTripByIdAsync(
        @HeaderMap mapOfHeaders: Map<String, String>,
        @Path("id") tripId: Int
    ): Deferred<Response<TripByIdResponse>>

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
    ): Deferred<Response<TripsResponseItem>>

    @PUT("/api/v1/update_place_status")
    fun updateStatusAsync(
        @HeaderMap mapOfHeaders: Map<String, String>,
        @Body requestBody: StatusRequest
    ): Deferred<Response<StatusResponse>>

    @POST("/api/v1/change_password")
    fun changePasswordAsync(
        @HeaderMap mapOfHeaders: Map<String, String>,
        @Body requestBody: PasswordRequest
    ): Deferred<Response<PasswordResponse>>

    @GET("/api/v1/filter_data")
    fun getFiltersAsync(
        @HeaderMap mapOfHeaders: Map<String, String>
    ): Deferred<Response<FiltersResponse>>
}
