package com.diu.swan.app.ui.doctor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.diu.swan.app.databinding.ActivityDocDetailsBinding
import com.diu.swan.app.ui.models.Doctor

class DocDetails : AppCompatActivity() {
    private lateinit var binding: ActivityDocDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            finish()
        }

        val model = intent.getSerializableExtra("model") as Doctor?

        binding.name.text = model?.name.toString()
        binding.DocDegree.text = model?.degree.toString()
        binding.DocCat.text = model?.specalist.toString()
        binding.bmdcNo.text = "BMDC No : "  + model?.bmdc.toString()
        binding.locDetais.text = model?.details.toString().replace("/n" , "\n")
        binding.contact.text = model?.phone.toString()






    }
}