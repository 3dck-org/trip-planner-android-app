package com.example.tripplanner.repositories.avatars_update

import com.example.tripplanner.TripPlannerAPI
import com.example.tripplanner.repositories.BaseRepository
import javax.inject.Inject

class AvatarsRepository @Inject constructor(private val api: TripPlannerAPI) :
IAvatarsRepository, BaseRepository(){

}