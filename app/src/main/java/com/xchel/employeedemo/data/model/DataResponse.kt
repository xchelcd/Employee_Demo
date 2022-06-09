package com.xchel.employeedemo.data.model

data class DataResponse(
    val data: File,
    val code: Int,
    val success: Boolean
)

data class File(
    val file: String
)