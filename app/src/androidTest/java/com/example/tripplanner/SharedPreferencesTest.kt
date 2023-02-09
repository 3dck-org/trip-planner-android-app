package com.example.tripplanner

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnitRunner
import com.example.tripplanner.utils.sharedpreferences.EncryptedSharedPreferences
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

const val SHARED_PREF_CONST = "SHARED_PREF_CONST"

@RunWith(JUnit4::class)
class SharedPreferencesTest : AndroidJUnitRunner() {

    private var sharedPref: EncryptedSharedPreferences? = null

    @Before
    fun setup(){
        val context : Context = InstrumentationRegistry.getInstrumentation().targetContext
        sharedPref = EncryptedSharedPreferences(context)
    }

    @Test
    fun test_add_to_shared_pref(){
        sharedPref?.addPreference(SHARED_PREF_CONST, "120401240")
        assertEquals(sharedPref?.getPreference(SHARED_PREF_CONST), "120401240")
        sharedPref?.remove(SHARED_PREF_CONST)
        assertEquals(sharedPref?.getPreference(SHARED_PREF_CONST), "")
    }
}
