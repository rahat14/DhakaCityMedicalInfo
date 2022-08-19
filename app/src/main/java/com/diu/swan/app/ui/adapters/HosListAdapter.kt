package com.diu.swan.app.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.diu.swan.app.R
import com.diu.swan.app.ui.models.Hospital
import com.google.android.material.button.MaterialButton

class HosListAdapter(
    private val interaction: Interaction? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    var lstAddress: List<Hospital> = emptyList()
    var MainList: List<Hospital> = emptyList()
    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Hospital>() {

        override fun areItemsTheSame(
            oldItem: Hospital,
            newItem: Hospital
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Hospital,
            newItem: Hospital
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.row_top_doctors_item_horizontal,
                    parent,
                    false
                ),
                interaction
            )



    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Hospital>) {
        differ.submitList(ArrayList(list))
    }

    fun setParentList(list: List<Hospital>) {
        MainList = list
    }

    fun getList(): List<Hospital?> {
        return MainList
    }

    class ViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {
        val docImage: ImageView = itemView.findViewById(R.id.profile_image)
        val name: TextView = itemView.findViewById(R.id.doctorName)
        val HospitalCat: TextView = itemView.findViewById(R.id.doctorCat)
        val bookButton: MaterialButton = itemView.findViewById(R.id.book_button)
        fun bind(item: Hospital) {
            itemView.setOnClickListener {
              //  interaction?.onItemSelected(adapterPosition, item)
            }
            bookButton.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            name.text = item.name
            HospitalCat.text = item.phone

            docImage.setImageDrawable(ContextCompat.getDrawable(itemView.context , R.drawable.hospital))


        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Hospital)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val filterResults = FilterResults()
                if (charSequence.isEmpty()) {

                } else {
                    val searchChr = charSequence.toString().lowercase()
                    val resultData: MutableList<Hospital> = ArrayList()

                    for (userModel in getList()) {
                        if (userModel?.name?.isNotEmpty() == true) {
                            if (userModel.name.toString().lowercase()
                                    .contains(searchChr) || userModel.address?.lowercase()
                                    ?.contains(
                                        searchChr
                                    ) == true
                            ) {
                                resultData.add(userModel)
                            }
                            Log.d("TAG", "${userModel.name}")
                        } else {
                            Log.d("TAG", "performFiltering:  $searchChr")
                        }
                    }
                    Log.d("TAG", "size: ${resultData.size}")
                    filterResults.values = resultData
                    filterResults.count = resultData.size
                }
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                if (filterResults.count > 0) {
                    val filteredList = filterResults.values as List<Hospital>

                    submitList(filteredList)
                } else {

                    submitList(emptyList())
                }


            }
        }
    }
}
