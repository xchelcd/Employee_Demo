package com.xchel.employeedemo.domain

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.widget.Toast
import com.google.gson.Gson
import com.xchel.employeedemo.data.model.DataResponse
import com.xchel.employeedemo.data.model.Employee
import com.xchel.employeedemo.data.model.File
import com.xchel.employeedemo.data.model.ResponseZip
import com.xchel.employeedemo.data.network.EmployeeService
import com.xchel.employeedemo.data.repository.EmployeeRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.nio.charset.Charset
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import javax.inject.Inject


class FetchData @Inject constructor(
    private val service: EmployeeService,
    private val repository: EmployeeRepository,
    @ApplicationContext private val context: Context
) {

    private val TAG = this.javaClass.simpleName

    private var fileName = ""
    private var zipName = ""

    suspend operator fun invoke(callback: () -> Unit): DataResponse? {
        val response = service.fetchData()
        response?.let { data ->
            if (data.success) {
                zipName = downloadFile(data.data)
                //fileName = unpackZip(
                //    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + "/",
                //    zipName
                //)
                //val jsonString = readJsonFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + "/", fileName)
                //getListObject(jsonString)
            }
        } ?: also { callback() }
        return response
    }

    private fun getListObject(jsonString: String) {
        Log.d(TAG, jsonString)
        val obj = Gson().fromJson(jsonString, ResponseZip::class.java)
        val list = obj.data.employees
        Log.d(TAG, list.toString())
        list.forEach { insertData(it) }
    }

    private fun insertData(employee: Employee) {
        repository.insertEmployeeToFirestore(employee) { flag, id ->
            CoroutineScope(Dispatchers.IO).launch {
                if (flag) {
                    repository.insertEmployeeToLocal(employee)
                }
            }
        }
    }

    private fun readJsonFile(path: String, jsonName: String): String {
        val yourFile = java.io.File(path + jsonName)
        val stream = FileInputStream(yourFile)
        var jString: String? = null
        jString = stream.use { stream ->
            val fc: FileChannel = stream.channel
            val bb: MappedByteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size())
            /* Instead of using default, pass in a decoder. */Charset.defaultCharset().decode(bb)
            .toString()
        }
        return jString.replace("\n", "").replace("\t", "")
    }

    // file:///storage/emulated/0/Download/employees_data.json.zip
    private fun downloadFile(data: File): String {
        onCompleted()
        val url = data.file
        val request = DownloadManager.Request(Uri.parse(url))
        val title = URLUtil.guessFileName(url, null, null)
        request.setTitle(title)
        request.setDescription("Downloading file, please wait")
        val cookie = CookieManager.getInstance().getCookie(url)
        request.addRequestHeader("cookie", cookie)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title)
        val downloadManager: DownloadManager =
            context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
        Toast.makeText(context, "Downloading started", Toast.LENGTH_SHORT).show()
        return title
    }

    private fun unpackZip(path: String, zipName: String): String {
        val `is`: InputStream
        val zis: ZipInputStream
        var filename = ""
        try {
            `is` = FileInputStream(path + zipName)
            zis = ZipInputStream(BufferedInputStream(`is`))
            var ze: ZipEntry
            val buffer = ByteArray(1024)
            var count: Int
            while (zis.nextEntry.also { ze = it } != null) {
                filename = ze.name

                // Need to create directories if not exists, or
                // it will generate an Exception...
                if (ze.isDirectory) {
                    val fmd = java.io.File(path + filename)
                    fmd.mkdirs()
                    continue
                }
                val fout = FileOutputStream(path + filename)
                while (zis.read(buffer).also { count = it } != -1) {
                    fout.write(buffer, 0, count)
                }
                fout.close()
                zis.closeEntry()
            }
            zis.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return filename
        } catch (e: Exception) {
            Log.d(TAG, e.toString())
            return filename
        }
        return filename
    }

    private fun onCompleted() {
        val receiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val action = intent.action
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == action) {
                    Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show()
                    fileName = unpackZip(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + "/",
                        zipName
                    )
                    val jsonString = readJsonFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + "/", fileName)
                    getListObject(jsonString)
                }
            }
        }

        context.registerReceiver(
            receiver, IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE
            )
        )
    }

    //https://firebasestorage.googleapis.com/v0/b/example-e6943.appspot.com/o/employees_data.json.zip?alt=media&token=02daec6d-cd37-48eb-bfa5-da5862f40b97
}

// Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
// /storage/emulated/0/Download/employees_data.json.zip
// java.io.File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path}/employees_data.json.zip").exists()
fun isFileExists(fileName: String): Boolean {
    val folder1 =
        java.io.File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path}/${fileName}")
    return folder1.exists()
}

fun deleteFile(fileName: String): Boolean {
    val folder1 =
        java.io.File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path}/${fileName}")
    return folder1.delete()
}