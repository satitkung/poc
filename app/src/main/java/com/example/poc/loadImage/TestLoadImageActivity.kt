package com.example.poc.loadImage

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.poc.GlideApp
import com.example.poc.R
import kotlinx.android.synthetic.main.activity_test_load_image.*
import android.graphics.Bitmap
import android.content.ContextWrapper
import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Build
import com.example.poc.Utils.PATH_LOGO_APP
import com.example.poc.Utils.SharedPreferenceManager
import com.example.poc.Utils.toBitmap
import com.tbruyelle.rxpermissions2.RxPermissions

import java.io.*


const val URL_PICTURE_1 = "https://i.ytimg.com/vi/6R5UveljmOQ/maxresdefault.jpg"
class TestLoadImageActivity : AppCompatActivity() {

    private val sharedPreferences by lazy {
        SharedPreferenceManager(this)
    }

    private val isAppLogoLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_load_image)

        requestPermission()
    }

    @SuppressLint("CheckResult")
    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            RxPermissions(this)
                .request(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe {  grant ->
                    if (grant) {
                        setupView(isAppLogoLoaded)
                    }
                }
        }
    }

    private fun setupView(appLogoLoaded: Boolean) {
        if (!appLogoLoaded) {
            GlideApp
                .with(this)
                .load(URL_PICTURE_1)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.d("TEE", "onLoadFailed: ")
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        resource?.let {
                            saveToInternalStorage(it.toBitmap())
                        }
                        Log.d("TEE", "onResourceReady: ")
                        return false
                    }

                })
                .into(img_1)
        } else {
            img_1.setImageBitmap(loadImageFromStorage(sharedPreferences.getStirng(PATH_LOGO_APP)))
        }
    }

    private fun saveToInternalStorage(bitmapImage: Bitmap) {
        val cw = ContextWrapper(applicationContext)
        // path to /data/data/yourapp/app_data/imageDir
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
        // Create imageDir
        val mypath = File(directory, "app_logo.jpg")

        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(mypath)
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 75, fos)
            sharedPreferences.save(directory.absolutePath, PATH_LOGO_APP)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    private fun loadImageFromStorage(path: String): Bitmap? {
        var b: Bitmap? = null
        try {
            val f = File(path, "app_logo.jpg")
            b = BitmapFactory.decodeStream(FileInputStream(f))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return b
    }
}
