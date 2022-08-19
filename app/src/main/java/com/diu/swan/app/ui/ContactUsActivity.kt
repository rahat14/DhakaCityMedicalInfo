package com.diu.swan.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.diu.swan.app.R
import com.diu.swan.app.databinding.ActivityAmbulanceListBinding
import com.diu.swan.app.databinding.ActivityContactUsBinding

class ContactUsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityContactUsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}