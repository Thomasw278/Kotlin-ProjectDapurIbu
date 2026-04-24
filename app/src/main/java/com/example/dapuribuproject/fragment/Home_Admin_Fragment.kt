package com.example.dapuribuproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.dapuribuproject.R
import android.widget.LinearLayout
import android.widget.TextView
import com.example.dapuribuproject.Helper.DatabaseHelper
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

        // Ambil Nama
        val nama_admin = view.findViewById<TextView>(R.id.namaadmin)
        val username = activity?.intent?.getStringExtra("username") ?: "User"
        nama_admin.text = username

        // Ambil Item Layout Tiap XML
        val container = view.findViewById<LinearLayout>(R.id.containerStatistik)
        val total_resep = view.findViewById<TextView>(R.id.tvTotalResep)

        // DB Helper || Ambil Size DB untuk Total Resep
        db = DatabaseHelper(requireContext())
        var panjang_data = db.getAllDataKatalog().size
        total_resep.text = panjang_data.toString()

        // Kategori Makanan
        var kategori = db.getAllDataKatalog()
        var nama = arrayListOf<String>()
        for (item in kategori) {
            if (item.kategori_katalog !in nama) {
                nama.add(item.kategori_katalog)
            }
        }

        // TV Total User | Total Admin
        val total_user = view.findViewById<TextView>(R.id.tvTotalUser)
        val total_admin = view.findViewById<TextView>(R.id.tvTotalAdmin)

        var hitung_user = 0
        var hitung_admin = 0

        for(item in db.getAllDataUser()) {
            var dummy = item.role
            if(dummy.equals("admin")){
                hitung_admin += 1
            } else {
                hitung_user += 1
            }
        }

        total_user.text = hitung_user.toString()
        total_admin.text = hitung_admin.toString()


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