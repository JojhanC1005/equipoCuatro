package com.example.pico_botella_grupo4.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.pico_botella_grupo4.R
import com.example.pico_botella_grupo4.view.MainActivity

class SplashActivity : AppCompatActivity() {

    private val splashDuration: Long = 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val imgBottleSplash = findViewById<ImageView>(R.id.imgBottleSplash)
        val animation = AnimationUtils.loadAnimation(this, R.anim.splash_bottle_anim)
        imgBottleSplash.startAnimation(animation)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, splashDuration)
    }
}