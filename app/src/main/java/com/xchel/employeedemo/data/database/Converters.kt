package com.xchel.employeedemo.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.xchel.employeedemo.data.model.Location

class Converters {
    val gson = Gson()
    @TypeConverter
    fun fromLocation(location: Location): String {
        return gson.toJson(location)
    }

    @TypeConverter
    fun toLocation(value: String): Location {
        return gson.fromJson(value, Location::class.java)
    }
}