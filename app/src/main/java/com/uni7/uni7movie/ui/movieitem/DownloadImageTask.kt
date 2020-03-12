package com.uni7.uni7movie.movieitem

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import java.io.InputStream
import java.net.URL


internal class DownloadImageTask(private var bmImage: ImageView) :
    AsyncTask<String?, Void?, Bitmap?>() {
    override fun onPostExecute(result: Bitmap?) {
        bmImage.setImageBitmap(result)
    }

    override fun doInBackground(vararg params: String?): Bitmap? {
        val urlDisplay = params[0]
        var image: Bitmap? = null
        try {
            val inputStream: InputStream = URL(urlDisplay).openStream()
            image = BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            Log.e("Error", e.message)
            e.printStackTrace()
        }
        return image
    }
}