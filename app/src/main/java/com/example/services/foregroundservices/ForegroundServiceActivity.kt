package com.example.services.foregroundservices

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.services.R
import com.example.services.databinding.ActivityForegroundServiceBinding

class ForegroundServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForegroundServiceBinding

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted ->
        if(!isGranted){
            Log.d("Foreground Services", "Foreground Service is Notification Permission Denied")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityForegroundServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.P){
            if(ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            )!=PackageManager.PERMISSION_GRANTED){
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        binding.btnStartService.setOnClickListener {
            Intent(this, MyForegroundServices::class.java).also {
                startService(it)
            }
        }

        binding.btnStopService.setOnClickListener {
            Intent(this, MyForegroundServices::class.java).also {
                stopService(it)
            }
        }
    }
}