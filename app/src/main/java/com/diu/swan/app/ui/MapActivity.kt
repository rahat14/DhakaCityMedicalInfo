package com.diu.swan.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.diu.swan.app.R
import com.diu.swan.app.databinding.ActivityMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapActivity : AppCompatActivity() {

    private  lateinit var  binding : ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Async map

        // Async map
        val mapFragment = supportFragmentManager.findFragmentById(
            R.id.google_map
        ) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            addMarkers(googleMap)
        }



    }


    private fun addMarkers(googleMap: GoogleMap) {
        var lat = LatLng(intent.getDoubleExtra("lat" ,0.0) , intent.getDoubleExtra("lng" ,0.0))
       // places.forEach { place ->
            val marker = googleMap.addMarker(
                MarkerOptions()
                    .title("")
                    .position(lat)
            )
       // }

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lat , 10f ))
    }



}