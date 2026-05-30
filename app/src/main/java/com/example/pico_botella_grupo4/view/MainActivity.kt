package com.example.pico_botella_grupo4.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pico_botella_grupo4.R
import com.example.pico_botella_grupo4.view.fragment.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }
    }
}