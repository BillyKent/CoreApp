package me.doapps.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.doapps.core.databinding.RecyclerItemHomeBinding
import me.doapps.core.viewmodel.HomeViewModel

class HomeListAdapter(private val homeViewmodel: HomeViewModel) :
    RecyclerView.Adapter<HomeListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerItemHomeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = homeViewmodel.menuItems.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(homeViewmodel.menuItems[position])


    inner class ViewHolder(private val binding: RecyclerItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HomeViewModel.Destination) {
            binding.viewmodel = homeViewmodel
            binding.destination = item
            binding.executePendingBindings()
        }

    }

}