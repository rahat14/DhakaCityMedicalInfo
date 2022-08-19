package com.diu.swan.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.diu.swan.app.databinding.ActivityMainBinding
import com.diu.swan.app.ui.ContactUsActivity
import com.diu.swan.app.ui.ambulance.AmbulanceList
import com.diu.swan.app.ui.doctor.DocList
import com.diu.swan.app.ui.hospital.HosList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.findDoc.setOnClickListener {
            startActivity(Intent(applicationContext, DocList::class.java))
        }

        binding.allDoc.setOnClickListener {
            startActivity(Intent(applicationContext, HosList::class.java))

        }


        binding.medTest.setOnClickListener {
            startActivity(Intent(applicationContext, AmbulanceList::class.java))

        }

        binding.allMedTest.setOnClickListener {

            startActivity(Intent(applicationContext, ContactUsActivity::class.java))
        }

        binding.call.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:999")
            startActivity(intent)
        }


    }
}