package com.xchel.employeedemo.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.MarkerOptions
import java.io.Serializable

@Entity(tableName = "employee_table")
data class Employee(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Long? = null,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "location") val location: Location,
    @ColumnInfo(name = "mail") val mail: String,

    //@PrimaryKey(autoGenerate = false)
    //@ColumnInfo(name = "primary_id") var primaryId: String
) : Serializable {

    fun constructor() {}

    fun getMarker(): MarkerOptions = MarkerOptions()
        .position(location.getPosition())
        .title("$id.- $name ($mail)")

    fun toFirestore() = hashMapOf(
        "id" to id,
        "name" to name,
        "location" to hashMapOf(
            "lat" to location.lat,
            "log" to location.log
        ),
        "mail" to mail,
        //"primaryId" to primaryId
    )
}