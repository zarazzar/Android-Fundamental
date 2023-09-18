package com.dicoding.firstgithubuser.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dicoding.firstgithubuser.R
import com.dicoding.firstgithubuser.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler(mainLooper)
        handler.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, DELAY)
    }

    companion object {
        private const val DELAY = 2000L
    }
}