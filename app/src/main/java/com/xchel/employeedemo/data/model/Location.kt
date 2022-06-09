package com.xchel.employeedemo.data.model

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.Serializable

data class Location(
    val lat: Double,
    val log: Double
) : Serializable {

    fun getPosition(): LatLng = LatLng(lat, log)
}
