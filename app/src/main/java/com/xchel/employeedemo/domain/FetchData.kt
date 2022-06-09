package com.xchel.employeedemo.domain

import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import com.xchel.employeedemo.data.model.DataResponse
import com.xchel.employeedemo.data.model.File
import com.xchel.employeedemo.data.network.EmployeeService
import com.xchel.employeedemo.util.DownloadFile
import com.xchel.employeedemo.util.Unzip
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class FetchData @Inject constructor(
    private val service: EmployeeService,
    @ApplicationContext private val context: Context
) {

    private val TAG = this.javaClass.simpleName

    suspend operator fun invoke(callback: () -> Unit): DataResponse? {
        val response = service.fetchData()
        response?.let { data ->
            if (data.success) unzipData(data.data)
        } ?: also { callback() }
        return response
    }

    //https://firebasestorage.googleapis.com/v0/b/example-e6943.appspot.com/o/employees_data.json.zip?alt=media&token=02daec6d-cd37-48eb-bfa5-da5862f40b97
    private fun unzipData(data: File) {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

    }

    private fun getPathDownloaded(): String =
        "${context.getExternalFilesDir(Environment.DIRECTORY_DCIM)}/dataDownloaded.zip"
        //Environment.getExternalStorageDirectory().toString() + "/dataDownloaded"

    private fun getPathDUnzipped(): String =
        "${context.getExternalFilesDir(Environment.DIRECTORY_DCIM)}/dataUnzipped.json"
    //Environment.getExternalStorageDirectory().toString() + "/dataDownloaded"
}