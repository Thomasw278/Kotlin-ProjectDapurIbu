package com.example.dapuribuproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.dapuribuproject.R

class Home_Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment [cite: 1]
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi Container dari XML
        val containerPopuler = view.findViewById<LinearLayout>(R.id.containerPopuler)
        val containerFavorit = view.findViewById<LinearLayout>(R.id.containerFavorit)

        // 1. Data Resep Populer
        val dataPopuler = arrayOf(
            Pair("Rendang Sapi", "500+ Dilihat"),
            Pair("Sate Ayam Madura", "420+ Dilihat")
        )

        for (item in dataPopuler) {
            val itemView = layoutInflater.inflate(R.layout.item_resep, containerPopuler, false)
            itemView.findViewById<TextView>(R.id.tvFoodName).text = item.first
            itemView.findViewById<TextView>(R.id.tvCategory).text = item.second

            // Atur lebar container scrooling
            val params = itemView.layoutParams
            params.width = 600
            itemView.layoutParams = params

            containerPopuler.addView(itemView)
        }

        // 2. Data Resep Favorit
        val dataFavorit = arrayOf(
            Pair("Sayur Asem Jakarta", "Favorit Saya"),
            Pair("Pudding Coklat Lumer", "Favorit Saya"),
            Pair("Menu MBG Bergizi", "Favorit Saya"),
            Pair("Pudding ESP 8266", "Favorit Saya")

        )

        for (item in dataFavorit) {
            val itemView = layoutInflater.inflate(R.layout.item_resep, containerFavorit, false)
            itemView.findViewById<TextView>(R.id.tvFoodName).text = item.first
            itemView.findViewById<TextView>(R.id.tvCategory).text = item.second

            containerFavorit.addView(itemView)
        }
    }
}