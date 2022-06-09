package com.xchel.employeedemo.ui.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xchel.employeedemo.data.model.DataResponse
import com.xchel.employeedemo.domain.ClearDatabase
import com.xchel.employeedemo.domain.FetchData
import com.xchel.employeedemo.domain.SyncData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val fetchDataFromService: FetchData,
    private val clearDatabase: ClearDatabase,
    private val syncData: SyncData
) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val data = MutableLiveData<DataResponse>()

    fun fetchData() {
        viewModelScope.launch {
            val response = fetchDataFromService {
                // something was wrong
            }
            if (response != null) data.postValue(response)
        }
    }

    fun clearData() {
        viewModelScope.launch { clearDatabase() }
    }

    fun fetchDataFromFirestore() {
        viewModelScope.launch { syncData() }
    }
}