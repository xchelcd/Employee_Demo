package com.xchel.employeedemo.data.network

import com.xchel.employeedemo.data.model.DataResponse
import com.xchel.employeedemo.data.model.File
import retrofit2.Response
import javax.inject.Inject

class EmployeeService @Inject constructor(
    private val api: EmployeeApiClient
) {

    suspend fun fetchData(): DataResponse? {
        val response: Response<DataResponse>? = try {
            api.getData()
        } catch (e: Exception) {
            null
        }
        return response?.body()
    }
}