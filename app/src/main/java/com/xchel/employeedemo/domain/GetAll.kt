package com.xchel.employeedemo.domain

import com.xchel.employeedemo.data.repository.EmployeeRepository
import javax.inject.Inject

class GetAll @Inject constructor(
    private val repository: EmployeeRepository
) {

    suspend operator fun invoke() = repository.getAllEmployees()
}