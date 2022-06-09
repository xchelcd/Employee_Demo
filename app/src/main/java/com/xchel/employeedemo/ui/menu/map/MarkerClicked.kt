package com.xchel.employeedemo.ui.menu.map

import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class MarkerClicked: GoogleMap.OnMarkerClickListener {

    private val TAG = this.javaClass.simpleName

    override fun onMarkerClick(p0: Marker): Boolean {
        Log.d(TAG, p0.toString())
        return false
    }


}