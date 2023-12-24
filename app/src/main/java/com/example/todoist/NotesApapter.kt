package com.example.todoist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(
    private val addressList: ArrayList<String>,
    private val nodeList: ArrayList<String>,
    private val context: Context,
    private val itemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<NotesAdapter.AdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_design, parent, false)
        return AdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        holder.titleText.text = addressList[position]
        holder.nodeText.text = nodeList[position]
        holder.cardView.setOnClickListener { itemClickListener.invoke(position) }
    }

    override fun getItemCount(): Int {
        return addressList.size
    }

    inner class AdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.TodoTitleID)
        val nodeText: TextView = itemView.findViewById(R.id.TodoTextID)
        val cardView: CardView = itemView.findViewById(R.id.cardViewID)
    }
}
