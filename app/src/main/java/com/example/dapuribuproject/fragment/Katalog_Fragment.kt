package com.example.dapuribuproject.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dapuribuproject.R
import com.example.dapuribuproject.AddKatalogActivity
import com.example.dapuribuproject.Helper.DatabaseHelper
import com.example.dapuribuproject.Adapter.KatalogAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Katalog_Fragment : Fragment() {

    lateinit var rvKatalog : RecyclerView
    lateinit var db : DatabaseHelper
    private var isAdmin = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_katalog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fabAdd: FloatingActionButton = view.findViewById(R.id.fabAddResep)
        val fabDel: FloatingActionButton = view.findViewById(R.id.fabAddDeleteResep)

        fabAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddKatalogActivity::class.java)
            startActivity(intent)
        }

        // Logika Skenario Button
        if (isAdmin) {
            fabAdd.visibility = View.VISIBLE
            fabDel.visibility = View.VISIBLE

        } else {
            fabAdd.visibility = View.GONE
            fabDel.visibility = View.GONE
        }

        db = DatabaseHelper(requireContext())
        rvKatalog = view.findViewById(R.id.rvKatalog)
        rvKatalog.layoutManager = LinearLayoutManager(requireContext())
        showData()
    }

    fun showData() {
        val listdata = db.getAllDataKatalog()
        val adapter = KatalogAdapter(listdata)
        rvKatalog.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        showData()
    }

}