package com.xchel.employeedemo.ui.menu.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xchel.employeedemo.data.model.Employee
import com.xchel.employeedemo.domain.GetAll
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getAll: GetAll
) : ViewModel() {

    val employees = MutableLiveData<List<Employee>>()

    fun getAllEmployees() {
        viewModelScope.launch {
            val response = getAll()
            employees.postValue(response)
        }
    }

    var isMapReady = MutableLiveData<Boolean>()
}