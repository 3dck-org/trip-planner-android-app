package com.example.tripplanner

import android.app.Application
import android.content.IntentFilter
import com.example.tripplanner.utils.recievers.Receiver
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application(){
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(TimberConfig())
        }
        initInternetReceiver()
    }

    private fun initInternetReceiver(){
        val receiver = Receiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(receiver, intentFilter)
    }
}