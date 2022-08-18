package com.diu.swan.app.ui.hospital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.diu.swan.app.R
import com.diu.swan.app.databinding.ActivityDocListBinding
import com.diu.swan.app.databinding.ActivityHosListBinding
import com.diu.swan.app.ui.Constants
import com.diu.swan.app.ui.adapters.DocListAdapter
import com.diu.swan.app.ui.adapters.HosListAdapter
import com.diu.swan.app.ui.models.Doctor
import com.diu.swan.app.ui.models.Hospital
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HosList : AppCompatActivity(), HosListAdapter.Interaction {
    private lateinit var binding: ActivityHosListBinding
    private lateinit var mAdapter: HosListAdapter
    private var docList: MutableList<Hospital> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHosListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAdapter = HosListAdapter(this)

        binding.docList.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@HosList)
        }
    }

    override fun onStart() {
        loadDataFromFirebase()
        super.onStart()
    }

    private fun loadDataFromFirebase() {

        val mref = FirebaseDatabase.getInstance(Constants.link).getReference("hospitals")
        docList.clear()
        mref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val model = postSnapshot.getValue(Hospital::class.java)
                    if (model != null) {
                        docList.add(model)
                    }

                }

                mAdapter.submitList(docList)
                mAdapter.setParentList(docList)


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    override fun onItemSelected(position: Int, item: Hospital) {

    }


}