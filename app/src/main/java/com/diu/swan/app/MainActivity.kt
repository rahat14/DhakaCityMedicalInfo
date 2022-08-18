package com.diu.swan.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.diu.swan.app.databinding.ActivityDocListBinding
import com.diu.swan.app.databinding.ActivityMainBinding
import com.diu.swan.app.ui.Constants
import com.diu.swan.app.ui.doctor.DocList
import com.diu.swan.app.ui.models.Ambulance
import com.diu.swan.app.ui.models.Hospital
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private  lateinit var  binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.findDoc.setOnClickListener {
            startActivity(Intent(applicationContext  , DocList::class.java))
        }

    }
}