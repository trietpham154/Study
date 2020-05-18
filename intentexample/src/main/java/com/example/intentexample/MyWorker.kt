package com.example.intentexample

import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.io.*
import java.net.*


class MyWorker(appContext: Context, workerParameters: WorkerParameters) :
    Worker(appContext, workerParameters) {

    override fun doWork(): Result {

        val inputStream = getImage(inputData.getString(Constant.KEY_IMAGE_URI))

        Log.i("MyWorker", "doWork()")

        val imagePath = saveImageToInternalStorage(inputStream)

        val outputData = workDataOf(Constant.OUTPUT_KEY to imagePath)

        return Result.success(outputData)
    }

    private fun getImage(imageURL: String?): InputStream? {

        var image: InputStream? = null

        try {
            val url = URL(imageURL)
            val urlConnection: URLConnection = url.openConnection()
            urlConnection.connect()
            image = urlConnection.getInputStream()
        } catch (e: IOException) { // openConnection(), connect()
            e.printStackTrace()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: UnknownServiceException) {
            e.printStackTrace()
        } catch (e: SocketTimeoutException) { // connect()
            e.printStackTrace()
        }

        Log.i("getImage into MyWorker", "completed")


        return image

    }

    private fun saveImageToInternalStorage(inputStream: InputStream?): String {
        val cw = ContextWrapper(applicationContext)

        val dir = cw.getDir("imageDir", Context.MODE_PRIVATE)

        val path = File(dir, Constant.IMAGE_NAME)

        var outputStream: OutputStream? = null

        try {
            outputStream = FileOutputStream(path)
            inputStream?.copyTo(outputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } finally {
            try {
                outputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return dir.absolutePath
    }
}