package com.diu.swan.app.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.diu.swan.app.MainActivity
import com.diu.swan.app.R
import com.diu.swan.app.ui.models.Doctor
import com.diu.swan.app.ui.models.Hospital
import com.google.firebase.database.FirebaseDatabase

class SpalshScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh_screen)








        Handler(Looper.getMainLooper()).postDelayed({
            /* Create an Intent that will start the Menu-Activity. */
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }, 800)

    }
}