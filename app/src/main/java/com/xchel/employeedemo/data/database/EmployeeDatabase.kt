package com.xchel.employeedemo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.xchel.employeedemo.data.database.dao.EmployeeDao
import com.xchel.employeedemo.data.model.Employee


@Database(entities = [Employee::class], version = 1)
@TypeConverters(Converters::class)
abstract class EmployeeDatabase: RoomDatabase() {

    abstract fun getEmployeeDao(): EmployeeDao
}