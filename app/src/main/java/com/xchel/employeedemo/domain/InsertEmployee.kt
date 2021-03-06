package com.xchel.employeedemo.domain

import com.xchel.employeedemo.data.model.Employee
import com.xchel.employeedemo.data.repository.EmployeeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class InsertEmployee @Inject constructor(
    private val repository: EmployeeRepository
) {

    suspend operator fun invoke(employee: Employee, wasAdded: () -> Unit) {
        val id = repository.insertEmployeeToLocal(employee)
        employee.id = id
        repository.insertEmployeeToFirestore(employee) {
            if (it) wasAdded()
        }
    }
}