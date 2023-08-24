package com.csroid.taskmosaic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DISPLAY_LENGTH: Long = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({

            val pref=getSharedPreferences("login", MODE_PRIVATE)
            var isLoggedIn:Boolean = pref.getBoolean("isLoggedIn",false)

            var iNext:Intent

            if(isLoggedIn) {
                iNext=Intent(this@SplashActivity, MainActivity::class.java)
            } else {
                iNext=Intent(this@SplashActivity,LoginActivity::class.java)
            }
            startActivity(iNext)
            finish()
        }, SPLASH_DISPLAY_LENGTH)
    }
}