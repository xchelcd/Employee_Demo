package com.xchel.employeedemo.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.xchel.employeedemo.data.database.dao.EmployeeDao
import com.xchel.employeedemo.data.model.Employee
import com.xchel.employeedemo.data.model.Location
import javax.inject.Inject

class EmployeeRepository @Inject constructor(
    //private val api: EmployeeService,
    private val employeeDao: EmployeeDao,
    private val fireStoreDb: FirebaseFirestore
) {

    private val TAG = this.javaClass.simpleName

    suspend fun getAllEmployees(): List<Employee> =
        employeeDao.getAllEmployees()

    /**
     * @return true if the employee is inserted in remote database
     */
    fun insertEmployeeToFirestore(employee: Employee, wasAdded: (Boolean, String) -> Unit) {
        fireStoreDb.collection(EmployeePath.Employee.name)
            .add(employee.toFirestore()).addOnSuccessListener {
                wasAdded(true, it.id)
            }.addOnFailureListener {
                wasAdded(false, "")
            }
    }

    suspend fun insertEmployeeToLocal(employee: Employee) {
        employeeDao.insertEmployee(employee)
    }

    suspend fun deleteEmployee(employee: Employee) {

    }

    fun syncData(callback: (List<Employee>?) -> Unit) {
        fireStoreDb.collection(EmployeePath.Employee.name)
            .get()
            .addOnSuccessListener { snapShot ->
                if (!snapShot.isEmpty) {
                    val list = mutableListOf<Employee>()
                    for (doc in snapShot) {
                        Log.d(TAG, "${doc.id} => ${doc.data}")
                        val data = doc.data
                        list.add(
                            Employee(
                                (data["id"] as Long).toInt(),
                                data["name"] as String,
                                convertToLocation(data["location"] as HashMap<String, Double>),
                                data["mail"] as String,
                                data["primaryId"] as String
                            )
                        )
                    }

                    callback(list)
                } else callback(null)
            }.addOnFailureListener {
                callback(null)
            }
    }

    private fun convertToLocation(map: HashMap<String, Double>): Location =
        Location(map["lat"] as Double, map["log"] as Double)

}

enum class EmployeePath {
    Employee
}