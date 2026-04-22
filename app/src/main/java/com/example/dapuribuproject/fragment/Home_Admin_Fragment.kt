package com.example.dapuribuproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dapuribuproject.R
import android.widget.LinearLayout
import android.widget.TextView
import com.example.dapuribuproject.adapterhelper.DatabaseHelper
import android.util.Log
import android.widget.ProgressBar

class Home_Admin_Fragment : Fragment() {

    private lateinit var db: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment [cite: 1]
        return inflater.inflate(R.layout.fragment_home_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil Item Layout Tiap XML
        val container = view.findViewById<LinearLayout>(R.id.containerStatistik)
        val total_resep = view.findViewById<TextView>(R.id.tvTotalResep)

        // DB Helper || Ambil Size DB untuk Total Resep
        db = DatabaseHelper(requireContext())
        var panjang_data = db.getAllData().size
        total_resep.text = panjang_data.toString()

        // Kategori Makanan
        var kategori = db.getAllData()
        var nama = arrayListOf<String>()
        for (item in kategori) {
            if (item.kategori_katalog !in nama) {
                nama.add(item.kategori_katalog)
            }
        }

        // Jumlah Per Kategori
        val kategori_jumlah = kategori.groupingBy { it.kategori_katalog }.eachCount()

        // Mapping Kategori Dengan Jumlah
        for (item in kategori_jumlah) {
            val barView = layoutInflater.inflate(R.layout.item_statistik_bar, container, false)
            barView.findViewById<TextView>(R.id.tvKategori).text = item.key
            barView.findViewById<TextView>(R.id.tvJumlahResep).text = item.value.toString()
            barView.findViewById<ProgressBar>(R.id.pbStatistik).progress = item.value.toInt()
            container.addView(barView)
        }
    }
}