package com.example.tripplanner.sharedpreferences

import com.example.tripplanner.constants.Constants
import com.example.tripplanner.models.UserInformation

class UserContainer(val sharedPref: EncryptedSharedPreferences) {

    private var _currentUser: UserInformation? = null

    var currentUser: UserInformation
        get() = _currentUser
            ?: throw UninitializedPropertyAccessException("\"currKid\" was queried before being initialized")
        set(value) {
            _currentUser = value;
            sharedPref.addPreference(Constants.KID_CHOISE_PROFILE_UUID_KEY, value.login)
        }


    fun isInit() =
        ((sharedPref.sharedPreferences.getString(Constants.KID_CHOISE_PROFILE_UUID_KEY, "")
            ?: "").isNotEmpty() && _currentUser != null)
}