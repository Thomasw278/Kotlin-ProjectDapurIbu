package com.example.dapuribuproject.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dapuribuproject.R

class ResepAdapter(private val nama: Array<String>, private val kategori: Array<String>) :
    RecyclerView.Adapter<ResepAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.tvFoodName)
        val tvKat: TextView = view.findViewById(R.id.tvCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_resep, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvNama.text = nama[position]
        holder.tvKat.text = "Kategori: ${kategori[position]}"
    }

    override fun getItemCount(): Int = nama.size
}