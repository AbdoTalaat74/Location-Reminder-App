package com.udacity.project4.locationreminders.geofence

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.udacity.project4.locationreminders.geofence.GeofenceTransitionsJobIntentService.Companion.enqueueWork
import com.udacity.project4.locationreminders.savereminder.SaveReminderFragment
import com.udacity.project4.utils.GeofencingConstants.ACTION_GEOFENCE_EVENT
import com.udacity.project4.utils.errorMessage

/**
 * Triggered by the Geofence.  Since we can have many Geofences at once, we pull the request
 * ID from the first Geofence, and locate it within the cached data in our Room DB
 *
 * Or users can add the reminders and then close the app, So our app has to run in the background
 * and handle the geofencing in the background.
 * To do that you can use https://developer.android.com/reference/android/support/v4/app/JobIntentService to do that.
 *
 */

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    private val TAG: String = "GeofenceBroadcastReceiver"

    @SuppressLint("LongLogTag")
    override fun onReceive(context: Context, intent: Intent) {
//TODO: implement the onReceive method to receive the geofencing events at the background

        Log.e(TAG, "onReceive")
        Log.i(TAG,"ACTION_GEOFENCE_EVENT1:"+ACTION_GEOFENCE_EVENT)
        Log.i(TAG,"ACTION_GEOFENCE_EVENT2:"+intent.action.toString())

        if (intent.action == ACTION_GEOFENCE_EVENT) {
            Log.i(TAG, ACTION_GEOFENCE_EVENT)
            val geofenceEvent = GeofencingEvent.fromIntent(intent)
            if (geofenceEvent.hasError()) {
                val error = errorMessage(context, geofenceEvent.errorCode)
                Log.e("GeofenceBroadcast", error)
                return
            }
            enqueueWork(context, intent)

        }

    }
}
