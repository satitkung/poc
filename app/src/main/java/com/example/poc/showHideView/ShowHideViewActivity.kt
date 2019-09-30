package com.example.poc.showHideView

import android.animation.ObjectAnimator
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.example.poc.MainActivity
import com.example.poc.R
import kotlinx.android.synthetic.main.activity_show_hide_view.*
import java.util.*

class ShowHideViewActivity : AppCompatActivity() {
    var isExpand = false
    lateinit var anim: ObjectAnimator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_hide_view)

        setNotification()
//        setupView()
    }

    //need to run in background service
    private fun setNotification(): Int {
        val title = "สวัสดีวันจันทร์"
        val body = "สวัสดีวันจันทร์  วันนี้มีโปรโมชั่น"
        val newIntent = Intent(this, MainActivity::class.java)
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            (0..10).random(),
            newIntent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.drawable.down_arrow)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .apply {
//                previewPhoto?.let {
//                    this.setLargeIcon(previewPhoto)
//                        .setStyle(
//                            NotificationCompat.BigPictureStyle()
//                                .bigLargeIcon(null)
//                                .bigPicture(it)
//                        )
//                }
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    this.priority = NotificationManager.IMPORTANCE_HIGH
//                } else {
//                    this.priority = Notification.PRIORITY_HIGH
//                }
            }
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setDefaults(Notification.DEFAULT_LIGHTS)

        notificationManager.notify(Date().time.toInt(), notificationBuilder.build())
        return Date().time.toInt()
    }


    private fun setupView() {
        column.setOnClickListener {
            if (isExpand) {
                isExpand = false
                anim = ObjectAnimator.ofFloat(img_arrow, "rotation", 0f)
                anim.duration = 1000
                anim.start()
            } else {
                isExpand = true
                anim = ObjectAnimator.ofFloat(img_arrow, "rotation", -180f)
                anim.duration = 1000
                anim.start()
            }
        }
    }
}
