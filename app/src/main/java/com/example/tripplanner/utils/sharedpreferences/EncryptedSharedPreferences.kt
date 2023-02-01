package com.example.tripplanner.utils.sharedpreferences

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class EncryptedSharedPreferences(val context: Context) {

    private val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "tripplanner_preferences",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun remove(preference: String){
        sharedPreferences.edit(true) {
            remove(preference)
        }
    }

    fun addPreference(preference: String, value: String){
        sharedPreferences.edit(true) {
            putString(preference, value)
        }
    }

    fun getPreference(preference: String) = sharedPreferences.getString(preference,"")

}
