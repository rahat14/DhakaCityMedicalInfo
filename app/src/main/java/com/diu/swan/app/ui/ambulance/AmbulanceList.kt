package com.diu.swan.app.ui.ambulance

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.diu.swan.app.databinding.ActivityAmbulanceListBinding
import com.diu.swan.app.ui.Constants
import com.diu.swan.app.ui.adapters.AmbListAdapter
import com.diu.swan.app.ui.models.Ambulance
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AmbulanceList : AppCompatActivity(), AmbListAdapter.Interaction {

    private lateinit var binding: ActivityAmbulanceListBinding
    private var docList: MutableList<Ambulance> = mutableListOf()
    private lateinit var mAdapter: AmbListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAmbulanceListBinding.inflate(layoutInflater)
        mAdapter = AmbListAdapter(this)
        binding.docList.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@AmbulanceList)
        }
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {

                if(binding.searchBar.query.isEmpty()){
                    mAdapter.submitList(docList)
                }else mAdapter.filter.filter(newText.lowercase())
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

        })


    }

    override fun onItemSelected(position: Int, item: Ambulance) {
        // calll init

    }


    override fun onStart() {
        loadDataFromFirebase()
        super.onStart()
    }

    private fun loadDataFromFirebase() {

        val mref = FirebaseDatabase.getInstance(Constants.link).getReference("ambulance")
        docList.clear()
        mref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val model = postSnapshot.getValue(Ambulance::class.java)
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
}