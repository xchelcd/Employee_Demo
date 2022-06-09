package com.xchel.employeedemo.util

import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream


class Unzip(private val zipFile: String, private val location: String) {
    fun unzip() {
        try {
            val fin = FileInputStream(zipFile)
            val zin = ZipInputStream(fin)
            var ze: ZipEntry? = null
            while (zin.nextEntry.also { ze = it } != null) {
                ze?.let {
                    Log.v("Decompress", "Unzipping " + ze!!.name)
                    if (ze!!.isDirectory) {
                        dirChecker(ze!!.name)
                    } else {
                        val fout = FileOutputStream(location + ze!!.name)
                        val buffer = ByteArray(8192)
                        var len: Int
                        while (zin.read(buffer).also { len = it } != -1) {
                            fout.write(buffer, 0, len)
                        }
                        fout.close()
                        zin.closeEntry()
                    }
                }
            }
            zin.close()
        } catch (e: Exception) {
            Log.d("Decompress", "unzip", e)
        }
    }

    private fun dirChecker(dir: String) {
        val f = File(location + dir)
        if (!f.isDirectory) {
            f.mkdirs()
        }
    }

    init {
        dirChecker("")
    }
}