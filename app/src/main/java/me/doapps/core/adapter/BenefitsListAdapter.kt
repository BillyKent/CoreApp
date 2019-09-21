package me.doapps.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.doapps.core.databinding.RecyclerBenefitItemBinding
import me.doapps.core.model.Benefit


open class BenefitsListAdapter(
    private val recyclerView: RecyclerView,
    var benefitList: List<Benefit> = listOf<Benefit>()

) : RecyclerView.Adapter<BenefitsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerBenefitItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = benefitList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(benefitList[position])

    private fun collapseAll() {
        for (index in benefitList.indices) {
            (recyclerView.findViewHolderForAdapterPosition(index) as ViewHolder).apply {
                collapse()
            }
        }
    }

    inner class ViewHolder(private val binding: RecyclerBenefitItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun collapse() {
            if(binding.expandableSubItem.isExpanded){
                binding.expandableSubItem.collapse()
                binding.arrow.rotation = 0f
                binding.arrow.animate().rotationBy(180f).start()
            }
        }
        fun expand(){
            binding.expandableSubItem.expand()
            binding.arrow.rotation= 180f
            binding.arrow.animate().rotationBy(-180f).start()
        }

        init {
            binding.cardexample.setOnClickListener {
                if (binding.expandableSubItem.isExpanded) {
                    collapse()
                } else {
                    collapseAll()
                    expand()
                }
            }
        }



        fun bind(benefit: Benefit) {

            if (benefit.serial == null || benefit.serial.trim().length == 0) {
                binding.usernameSubItem.root.visibility = View.VISIBLE
                binding.passwordSubItem.root.visibility = View.VISIBLE
                binding.serialSubItem.root.visibility = View.GONE
                binding.usernameSubItem.apply {
                    title = "Usuario"
                    content = benefit.username
                }
                binding.passwordSubItem.apply {
                    title = "Contraseña"
                    content = benefit.password
                }
            } else {
                binding.serialSubItem.root.visibility = View.VISIBLE
                binding.usernameSubItem.root.visibility = View.GONE
                binding.passwordSubItem.root.visibility = View.GONE
                binding.serialSubItem.apply {
                    title = "Serial"
                    content = benefit.serial
                }
            }

            binding.benefit = benefit
        }


    }
}