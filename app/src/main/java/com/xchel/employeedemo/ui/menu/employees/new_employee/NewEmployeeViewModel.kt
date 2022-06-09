package com.xchel.employeedemo.ui.menu.employees.new_employee

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xchel.employeedemo.data.model.Employee
import com.xchel.employeedemo.domain.InsertEmployee
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewEmployeeViewModel @Inject constructor(
    private val insertEmployee: InsertEmployee
): ViewModel() {

    val isLoading = MutableLiveData<Boolean>()

    fun saveEmployee(employee: Employee) {
        viewModelScope.launch {
            insertEmployee(employee) { // the user was added tof firestore

            }
        }
    }
}