package com.example.tripplanner.domain

data class TripPlaceInfo(
    val comment: String,
    val order: Int,
    val place: Place,
    val place_id: Int,
    val trip_id: Int
)

data class TripPlaceInfoWithStatus(
    val place: Place,
    val place_id: Int,
    val trip_id: Int,
    val journey_id: Int,
    val status: String
) {
    companion object {
        val placeInfosList : MutableList<TripPlaceInfoWithStatus> = mutableListOf()

        fun create(tripPlaceInfo: List<TripPlaceInfo>, journeyPlaceInfo: List<JourneyPlaceInfo>) : List<TripPlaceInfoWithStatus>{
            placeInfosList.clear()
            for (tripPlaceInfoElement in tripPlaceInfo) {
                journeyPlaceInfo.find { tripPlaceInfoElement.place_id == it.place_id }?.apply {
                    placeInfosList.add(
                        TripPlaceInfoWithStatus(
                            place = tripPlaceInfoElement.place,
                            place_id = tripPlaceInfoElement.place_id,
                            trip_id = tripPlaceInfoElement.trip_id,
                            journey_id = journey_id,
                            status = status
                        )
                    )
                }
            }
            return placeInfosList
        }

        }
}