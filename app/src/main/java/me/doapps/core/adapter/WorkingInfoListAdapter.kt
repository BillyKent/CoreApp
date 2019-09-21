package me.doapps.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.doapps.core.databinding.RecyclerDocumentItemBinding
import me.doapps.core.model.Document

class DocumentListAdapter : RecyclerView.Adapter<DocumentListAdapter.ViewHolder>() {

    lateinit var documentList: List<Document>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerDocumentItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = documentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(documentList[position])

    inner class ViewHolder(private val binding: RecyclerDocumentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(document: Document) {
            binding.document = document
        }
    }

}