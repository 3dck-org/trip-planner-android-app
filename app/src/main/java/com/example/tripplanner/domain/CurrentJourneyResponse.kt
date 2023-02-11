package com.example.tripplanner.domain

data class CurrentJourneyResponse(
    val completed: String,
    val created_at: String,
    val distance: String,
    val end_at: String,
    val id: Int,
    val start_at: String,
    val trip: Trip,
    val trip_id: Int,
    val updated_at: String,
    val user_id: Int,
    val journey_place_infos: List<JourneyPlaceInfo>
){
    fun mapCurJourneyToTrips(isLiked: Boolean) = Trips(TripsResponseItem(
        created_at = trip.created_at,
        description = trip.description,
        distance = trip.distance,
        isFavourite = !isLiked,
        duration = trip.duration,
        image_url = trip.image_url,
        id = trip_id,
        name = trip.name,
        updated_at = trip.updated_at,
        user_id = user_id.toString()
    ))
}