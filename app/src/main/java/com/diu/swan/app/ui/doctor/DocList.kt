package com.diu.swan.app.ui.doctor

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.diu.swan.app.databinding.ActivityDocListBinding
import com.diu.swan.app.ui.Constants
import com.diu.swan.app.ui.adapters.DocListAdapter
import com.diu.swan.app.ui.models.Doctor
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*


class DocList : AppCompatActivity(), DocListAdapter.Interaction {
    private lateinit var binding: ActivityDocListBinding
    private lateinit var mAdapter: DocListAdapter
    private var docList: MutableList<Doctor> = mutableListOf()
    private var fillterList : MutableList<Doctor> = mutableListOf()
    private var specialFilter = "All"
    var specilist = arrayOf(
        "All",
        "Cancer Specialist",
        "Cardiology Specialist",
        "Chest Diseases Specialist",
        "Child Diseases Specialist",
        "Diabetes Specialist",
        "Dental Specialist",
        "ENT Specialist",
        "Eye Specialist",
        "Gynecologist",
        "Liver Diseases Specialist",
        "Kidney Diseases Specialist",
        "Medicine Specialist",
        "Neurology Specialist",
        "Orthopedic Specialist",
        "Skin Specialist",
        "Urology Specialist"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDocListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            finish()
        }


        mAdapter = DocListAdapter(this)

        binding.docList.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@DocList)
        }

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {

//                if (binding.searchBar.query.isEmpty()) {
//                    mAdapter.submitList(docList)
//                } else mAdapter.filter.filter(newText.lowercase())
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

        })

        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            R.layout.simple_spinner_item,
            specilist
        )

        // set simple layout resource file
        // for each item of spinner

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
            R.layout.simple_spinner_dropdown_item
        )

        binding.spinner.adapter = ad

        binding.spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                 specialFilter = specilist[position]


            }

        }

        binding.searchBtn.setOnClickListener {

            filterTheList(specialFilter)
        }
    }

    private fun filterTheList(specialFilter: String) {
        val list  = docList
       val searchQuery =  binding.searchBar.query.toString().toLowerCase(Locale.ROOT)
        fillterList.clear()

        for (item in list){

                if(specialFilter == "All"){
                    if (item.name.toString().lowercase()
                            .contains(searchQuery)
                        || item.specalist?.lowercase()
                            ?.contains(
                                searchQuery
                            ) == true
                        || item.degree?.lowercase()
                            ?.contains(
                                searchQuery
                            ) == true
                    ) {
                        fillterList.add(item)
                    }
                }
            else {
                    if (
                        (item.name.toString().lowercase()
                            .contains(searchQuery)
                                || item.specalist?.lowercase()
                            ?.contains(
                                searchQuery
                            ) == true
                                || item.degree?.lowercase()
                            ?.contains(
                                searchQuery
                            ) == true
                                )
                        && item.specalist.toString().contains(specialFilter)
                    ) {
                        fillterList.add(item)
                    }

                }
        }

        mAdapter.submitList(emptyList())
        mAdapter.submitList(fillterList)

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

        startActivity(Intent(applicationContext, DocDetails::class.java).apply {
            putExtra("model", item)
        })

    }


}