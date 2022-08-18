package com.diu.swan.app.ui.doctor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.diu.swan.app.databinding.ActivityDocListBinding
import com.diu.swan.app.ui.Constants
import com.diu.swan.app.ui.adapters.DocListAdapter
import com.diu.swan.app.ui.models.Doctor
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DocList : AppCompatActivity(), DocListAdapter.Interaction {
    private lateinit var binding: ActivityDocListBinding
    private lateinit var mAdapter: DocListAdapter
    private var docList: MutableList<Doctor> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDocListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAdapter = DocListAdapter(this)

        binding.docList.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@DocList)
        }
    }


    override fun onStart() {
        loadDataFromFirebase()
        super.onStart()
    }

    private fun loadDataFromFirebase() {

        val mref = FirebaseDatabase.getInstance(Constants.link).getReference("doctors")
        docList.clear()
        mref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val model = postSnapshot.getValue(Doctor::class.java)
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

    override fun onItemSelected(position: Int, item: Doctor) {

    }


}