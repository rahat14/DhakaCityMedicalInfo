package com.diu.swan.app.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.diu.swan.app.R
import com.diu.swan.app.databinding.RowForAmbulanceBinding
import com.diu.swan.app.ui.models.Ambulance

class AmbListAdapter(
    private val interaction: Interaction? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    var lstAddress: List<Ambulance> = emptyList()
    var MainList: List<Ambulance> = emptyList()
    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Ambulance>() {

        override fun areItemsTheSame(
            oldItem: Ambulance,
            newItem: Ambulance
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Ambulance,
            newItem: Ambulance
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_for_ambulance,
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

    fun submitList(list: List<Ambulance>) {
        differ.submitList(ArrayList(list))
    }

    fun setParentList(list: List<Ambulance>) {
        MainList = list
    }

    fun getList(): List<Ambulance?> {
        return MainList
    }

    class ViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        val binding = RowForAmbulanceBinding.bind(itemView)

        fun bind(item: Ambulance) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }


            binding.tvTitle.text = item.name.toString()
            binding.phone.text = item.phone.toString()


        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Ambulance)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val filterResults = FilterResults()
                if (charSequence.isEmpty()) {

                } else {
                    val searchChr = charSequence.toString().lowercase()
                    val resultData: MutableList<Ambulance> = ArrayList()

                    for (userModel in getList()) {
                        if (userModel?.name?.isNotEmpty() == true) {
                            if (userModel.name.toString().lowercase()
                                    .contains(searchChr)
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
                Log.d("TAG", "publishResults: ${filterResults.count} ")
                if (filterResults.count > 0) {
                    val filteredList = filterResults.values as List<Ambulance>

                    submitList(filteredList)
                } else {

                    submitList(emptyList())
                }

            }
        }
    }
}
