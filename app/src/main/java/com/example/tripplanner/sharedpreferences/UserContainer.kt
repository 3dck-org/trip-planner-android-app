package com.example.tripplanner.sharedpreferences

import com.example.tripplanner.constants.Constants
import com.example.tripplanner.models.UserDetails

class UserContainer(val sharedPref: EncryptedSharedPreferences) {

    private var _currentUser: UserDetails? = null

    var currentUser: UserDetails
        get() = _currentUser
            ?: throw UninitializedPropertyAccessException("\"currUser\" was queried before being initialized")
        set(value) {
            _currentUser = value
            sharedPref.addPreference(Constants.USER_ID, value.login)
        }


    fun isInit() =
        ((sharedPref.sharedPreferences.getString(Constants.USER_ID, "")
            ?: "").isNotEmpty() && _currentUser != null)
}