package com.xchel.employeedemo.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.xchel.employeedemo.data.model.Employee

@Dao
interface EmployeeDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertEmployee(employee: Employee): Long

    @Query("select * from employee_table")
    suspend fun getAllEmployees(): List<Employee>

    @Query("select count(*) from employee_table")
    suspend fun getTotalEmployees(): Int

    @Query("delete from employee_table")
    suspend fun clearDatabase()
}