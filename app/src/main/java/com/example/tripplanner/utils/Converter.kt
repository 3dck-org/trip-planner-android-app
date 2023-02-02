package com.example.tripplanner.utils

object Converter {

    fun convertDistance(distance: Float): String {
        val kilometers = distance.toInt() / 1000
        val meters = distance.toInt() % 1000
        return if(kilometers!=0&&meters!=0) {"${distance.toInt() / 1000}km ${distance.toInt() % 1000}m "}else{
            if(kilometers!=0){ "${distance.toInt() / 1000}km"} else { "${distance.toInt() % 1000}m" }
        }
    }

    fun convertDuration(duration: Int): String {
        val hours = duration / 3600
        val minutes = duration % 3600 / 60
        return if (hours != 0 && minutes != 0) {
            "${duration / 3600}h ${duration % 3600 / 60}m"
        } else {
            if (hours != 0) "${duration / 3600}h"
            else "${duration % 3600 / 60}m"
        }
    }
}