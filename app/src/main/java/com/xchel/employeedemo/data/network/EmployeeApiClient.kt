package com.xchel.employeedemo.data.network

import com.xchel.employeedemo.data.model.DataResponse
import retrofit2.Response
import retrofit2.http.GET

interface EmployeeApiClient {

    @GET("s/5u21281sca8gj94/getFile.json?dl=0")
    suspend fun getData(): Response<DataResponse>

}