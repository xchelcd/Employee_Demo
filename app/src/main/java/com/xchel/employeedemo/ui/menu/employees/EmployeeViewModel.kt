package com.xchel.employeedemo.ui.menu.employees

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xchel.employeedemo.data.model.Employee
import com.xchel.employeedemo.domain.GetAll
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(
    private val getAll: GetAll
) : ViewModel() {

    val employeeList = MutableLiveData<List<Employee>>()

    fun getAllEmployees() {
        viewModelScope.launch {
            val response = getAll()
            employeeList.postValue(response)
        }
    }
}