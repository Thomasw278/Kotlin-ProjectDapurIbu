package com.example.dapuribuproject.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dapuribuproject.R
import com.example.dapuribuproject.AddKatalogActivity
import com.example.dapuribuproject.Helper.DatabaseHelper
import com.example.dapuribuproject.Adapter.KatalogAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Katalog_Fragment : Fragment() {

    lateinit var rvKatalog : RecyclerView
    private var selectedKatalogId: Int? = null

    @Inject
    lateinit var db : DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_katalog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var isAdmin = activity?.intent?.getBooleanExtra("isAdmin", false)

        val fabAdd: FloatingActionButton = view.findViewById(R.id.fabAddResep)
        val fabDel: FloatingActionButton = view.findViewById(R.id.fabAddDeleteResep)

        fabAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddKatalogActivity::class.java)
            startActivity(intent)
        }

        fabDel.setOnClickListener {
            selectedKatalogId?.let { id ->
                db.deleteData_Katalog(id)
                selectedKatalogId = null //Reset Pilihan
                showData()
                Toast.makeText(requireContext(), "Menu berhasil dihapus", Toast.LENGTH_SHORT).show()
            } ?: run {
                Toast.makeText(requireContext(), "Pilih menu yang ingin dihapus terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }

        // Logika Skenario Button
        if (isAdmin == true) {
            fabAdd.visibility = View.VISIBLE
            fabDel.visibility = View.VISIBLE
        } else {
            fabAdd.visibility = View.GONE
            fabDel.visibility = View.GONE
        }

        rvKatalog = view.findViewById(R.id.rvKatalog)
        rvKatalog.layoutManager = LinearLayoutManager(requireContext())

        // SearchBar
        val searchbar = view.findViewById<EditText>(R.id.etSearch)
        searchbar.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if(query.isEmpty()){
                    showData()
                } else {
                    val listdata = db.Search(s.toString())
                    val adapter = KatalogAdapter(listdata) { selectedItem ->
                        selectedKatalogId = selectedItem.id_katalog
                    }
                    rvKatalog.adapter = adapter
                }
            }
        })
        showData()
    }

    fun showData() {
        val listdata = db.getAllDataKatalog()
        val adapter = KatalogAdapter(listdata) { selectedItem ->
            selectedKatalogId = selectedItem.id_katalog
        }
        rvKatalog.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        showData()
    }
}
