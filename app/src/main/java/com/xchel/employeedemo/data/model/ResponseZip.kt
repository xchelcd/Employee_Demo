package com.xchel.employeedemo.data.model

import com.xchel.employeedemo.data.model.Employee

data class ResponseZip (
    val data: ResponseData
)

data class ResponseData(
    val employees: List<Employee>
)
