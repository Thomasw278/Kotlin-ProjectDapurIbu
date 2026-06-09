package com.example.dapuribuproject.Adapter

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dapuribuproject.DataClass.Katalog
import com.example.dapuribuproject.DetailKatalogActivity
import com.example.dapuribuproject.R

class KatalogAdapter(
    private val listkatalog: List<Katalog>,
    private val isAdmin: Boolean,
    private val username: String,
    private val onItemClick: (Katalog) -> Unit
) : RecyclerView.Adapter<KatalogAdapter.ViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.tvFoodName)
        val tvKat: TextView = view.findViewById(R.id.tvCategory)
        val imgFoto: ImageView = view.findViewById(R.id.ivFood)
        val tvDetail: TextView = view.findViewById(R.id.tvStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_resep, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listkatalog[position]
        holder.tvNama.text = item.judul_katalog
        holder.tvKat.text = "Kategori : ${item.kategori_katalog}"
        
        Glide.with(holder.itemView.context)
            .load(item.foto_katalog)
            .placeholder(R.drawable.makanan)
            .into(holder.imgFoto)

        if (selectedPosition == position) {
            holder.itemView.setBackgroundColor(Color.LTGRAY)
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT)
        }

        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = holder.adapterPosition
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
            onItemClick(item)
        }

        holder.tvDetail.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailKatalogActivity::class.java)
            intent.putExtra("id_katalog", item.id_katalog)
            intent.putExtra("judul", item.judul_katalog)
            intent.putExtra("kategori", item.kategori_katalog)
            intent.putExtra("deskripsi", item.deskripsi_katalog)
            intent.putExtra("foto", item.foto_katalog)
            intent.putExtra("isAdmin", isAdmin)
            intent.putExtra("username", username)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listkatalog.size
}
