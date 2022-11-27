package com.example.tripplanner.extensions

import com.example.tripplanner.constants.Constants
import com.example.tripplanner.models.LoginRequest
import com.example.tripplanner.sharedpreferences.EncryptedSharedPreferences

class ExternalUserData constructor(val sharedPreferences: EncryptedSharedPreferences){

        fun getExternalUserData() = sharedPreferences.let {
            LoginRequest(
                refresh_token = it.sharedPreferences.getString(Constants.REFRESH_TOKEN, "") ?: "",
                client_secret = Constants.SECRET,
                client_id = Constants.CLIENT_ID,
                email = "zlochevsky000@gmail.com",
                grant_type = "password",
                password = "qqA1SDFgg@"
            )
        }
}