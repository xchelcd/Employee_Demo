package com.xchel.employeedemo.domain

import com.google.firebase.firestore.FirebaseFirestore
import com.xchel.employeedemo.data.database.dao.EmployeeDao
import com.xchel.employeedemo.data.model.Employee
import com.xchel.employeedemo.data.repository.EmployeeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class SyncData @Inject constructor(
    private val repository: EmployeeRepository,
    private val dao: EmployeeDao
) {

    suspend operator fun invoke() {
        repository.syncData {
            it?.let { employeeList ->
                CoroutineScope(IO).launch {
                    dao.clearDatabase()
                    employeeList.forEach { employee -> insertEmployee(employee) }
                }
            }
        }
    }

    private suspend fun insertEmployee(employee: Employee) {
        dao.insertEmployee(employee)
    }
}