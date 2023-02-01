package com.example.tripplanner.utils.sharedpreferences

import com.example.tripplanner.constants.Constants
import com.example.tripplanner.domain.UserInformation

class UserContainer(val sharedPref: EncryptedSharedPreferences) {

    private var _currentUser: UserInformation? = null

    var currentUser: UserInformation
        get() = _currentUser
            ?: throw UninitializedPropertyAccessException("\"currKid\" was queried before being initialized")
        set(value) {
            _currentUser = value;
            sharedPref.addPreference(Constants.KID_CHOICE_PROFILE_UUID_KEY, value.login)
        }


    fun isInit() =
        ((sharedPref.sharedPreferences.getString(Constants.KID_CHOICE_PROFILE_UUID_KEY, "")
            ?: "").isNotEmpty() && _currentUser != null)
}