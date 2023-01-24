package com.example.tripplanner.constants

object Constants {

    private const val MIN_PASS_LENGTH = 8
    const val passwordPattern = "^" +
            "(?=.*[a-z])" +
            "(?=.*[A-Z])" +
            "(?=.*[1-9])" +
            "(?=.*[!()*@#$%^&+=])" + "(?=\\S+$)" +
            ".{$MIN_PASS_LENGTH,}" +
            "$"
    const val BASE_URL = "https://trip-planner-api.tk/"
    const val CLIENT_ID = "HbPJN18dvU1XDoZTcFs4Rv0FX2UcylnRZv1VTPjrT0A"
    const val SECRET = "3oxXyFaGro3V1Ihg35z5zIv51AUTQ2EpikBEX-XnQdw"
    
    //SHARED PREFERENCES
    const val TOKEN = "TOKEN"
    const val REFRESH_TOKEN = "REFRESH_TOKEN"
    const val KID_CHOICE_PROFILE_UUID_KEY = "KID_CHOISE_PROFILE_UUID_KEY"

    //DATE
    const val dateFormat : String = "yyyy-MM-dd HH:mm:ss"
}