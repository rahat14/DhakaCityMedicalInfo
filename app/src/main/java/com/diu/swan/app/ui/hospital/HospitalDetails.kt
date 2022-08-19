package com.diu.swan.app.ui.hospital

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.diu.swan.app.databinding.ActivityHospitalDetailsBinding
import com.diu.swan.app.ui.models.Hospital


class HospitalDetails : AppCompatActivity() {
    private lateinit var binding: ActivityHospitalDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHospitalDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            finish()
        }

        val model = intent.getSerializableExtra("model") as Hospital?

        binding.name.text = model?.name.toString()
        binding.locDetais.text = model?.address.toString()
        binding.DocCat.text = "Estd. ${model?.estd.toString()}"
        binding.locDetais.text = model?.details.toString()

    }
}