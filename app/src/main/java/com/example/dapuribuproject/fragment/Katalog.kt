package com.example.dapuribuproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dapuribuproject.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Katalog : Fragment() {
    private val namaResep = arrayOf("Rendang Daging Sapi", "Sayur Asem Jakarta", "Nasi Goreng Kampung", "Pudding Coklat Lumer")
    private val kategoriResep = arrayOf("Hidangan Utama", "Sayuran", "Sarapan", "Pencuci Mulut")
    private var isAdmin = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_katalog, container, false)

        // 1. Inisialisasi RecyclerView
        val rvKatalog: RecyclerView = view.findViewById(R.id.rvKatalog)
        rvKatalog.layoutManager = LinearLayoutManager(context)

        // PASANG ADAPTER
        val adapter = ResepAdapter(namaResep, kategoriResep)
        rvKatalog.adapter = adapter

        // 2. Inisialisasi FloatingActionButton (FAB)
        val fabAdd: FloatingActionButton = view.findViewById(R.id.fabAddResep)
        val fabDel: FloatingActionButton = view.findViewById(R.id.fabAddDeleteResep)


        // Logika Skenario Button
        if (isAdmin) {
            fabAdd.visibility = View.VISIBLE
            fabDel.visibility = View.VISIBLE

        } else {
            fabAdd.visibility = View.GONE
            fabDel.visibility = View.GONE
        }

        return view
    }
}