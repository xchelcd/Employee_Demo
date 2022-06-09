package com.xchel.employeedemo.domain

import com.xchel.employeedemo.data.database.dao.EmployeeDao
import javax.inject.Inject

class ClearDatabase @Inject constructor(
    private val dao: EmployeeDao
) {
    suspend operator fun invoke() {
        dao.clearDatabase()
    }
}