package com.example.intentexample

import android.graphics.BitmapFactory
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageData = workDataOf(Constant.KEY_IMAGE_URI to Constant.IMAGE_URI_STRING)

        val myWorkRequest = OneTimeWorkRequestBuilder<MyWorker>()
            .setInputData(imageData)
            .build()

        val myWorkManager = WorkManager.getInstance(this)

        myWorkManager
            .enqueue(myWorkRequest)
        Log.i("MainActiviy", "Test Button")

        myWorkManager.getWorkInfoByIdLiveData(myWorkRequest.id).observe(this, Observer {
            if (it?.state == WorkInfo.State.SUCCEEDED) {
//                Toast.makeText(
//                    this,
//                    it.outputData.getString(Constant.OUTPUT_KEY),
//                    Toast.LENGTH_SHORT
//                ).show()
                it.outputData.getString(Constant.OUTPUT_KEY)?.let { path -> setImage(path) }
            }
        })
    }

    private fun setImage(path: String) {
        var fis: FileInputStream? = null

        try {
            fis = FileInputStream(File(path, Constant.IMAGE_NAME))
            BitmapFactory.Options().run {
                inSampleSize = 5 // scaleSize = originalSize / inSampleSize
                val bitmap = BitmapFactory.decodeStream(fis, null, this)
                image?.setImageBitmap(bitmap)
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } finally {
            try {
                fis?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

//    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
//        // Raw height and width of image
//        val (height: Int, width: Int) = options.run { outHeight to outWidth }
//        var inSampleSize = 1
//
//        if (height > reqHeight || width > reqWidth) {
//
//            val halfHeight: Int = height / 2
//            val halfWidth: Int = width / 2
//
//            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
//            // height and width larger than the requested height and width.
//            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
//                inSampleSize *= 2
//            }
//        }
//
//        return inSampleSize
//    }
}
