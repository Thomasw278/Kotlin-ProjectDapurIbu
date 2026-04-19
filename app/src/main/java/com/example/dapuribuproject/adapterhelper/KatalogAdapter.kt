package com.example.dapuribuproject.adapterhelper

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dapuribuproject.R

class KatalogAdapter(private val listkatalog : List<Katalog>) :
    RecyclerView.Adapter<KatalogAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.tvFoodName)
        val tvKat: TextView = view.findViewById(R.id.tvCategory)
        val imgFoto : ImageView = view.findViewById(R.id.ivFood)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_resep, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listkatalog[position]
        holder.tvNama.text = item.judul_katalog
        holder.tvKat.text = item.kategori_katalog

        // Ambil Foto Dari Drawable
        val context = holder.itemView.context
        val fotoName = item.foto_katalog
        val resource = context.resources.getIdentifier(fotoName, "drawable", context.packageName)
        if(resource != 0) {
            holder.imgFoto.setImageResource(resource)
        } else {
            holder.imgFoto.setImageResource(R.drawable.makanan)
        }
    }

    override fun getItemCount(): Int {
        return listkatalog.size
    }

}