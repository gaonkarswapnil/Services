package com.example.services.foregroundservices

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.services.R

class MyForegroundServices : Service() {
    companion object{
        const val CHANNEL_ID = "ForegroundServiceChannel"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Services")
            .setContentText("This is foreground Service Demo")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()

        startForeground(1, notification)

        Thread{
            for(i in 0..3){
                Thread.sleep(1000)
                Log.d("Foreground Services", "Foreground service is running: $i seconds")
            }
//            stopSelf()
        }.start()

        return START_NOT_STICKY

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Foreground Services", "Foreground Service is Destroyed here")
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d("Foreground Services", "Foreground service onBind")
       return null
    }
}