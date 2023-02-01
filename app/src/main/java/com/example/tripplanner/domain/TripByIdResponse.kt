package com.example.tripplanner.domain

data class TripByIdResponse(
    val id: Int,
    val user_id: String,
    val name: String,
    val description: String,
    val distance: String,
    val duration: Int,
    val image_url: String,
    val favorite: String,
    val created_at: String,
    val updated_at: String,
    val trip_place_infos: List<TripPlaceInfo>,
    val user: UserDetails,
){

    private fun mapTripByIdResponseToTripsModel() : Trips =
        Trips(
            TripsResponseItem(
                created_at = created_at,
                description = description,
                distance = distance,
                isFavourite = favorite.toBoolean(),
                duration = duration,
                image_url = image_url,
                id = id,
                name = name,
                updated_at = updated_at,
                user_id = user_id
            )
        )

    fun tripModelWithChangedLike(isLiked: Boolean) = Trips(mapTripByIdResponseToTripsModel().trip.copy(isFavourite = !isLiked))
}