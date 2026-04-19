package com.example.dapuribuproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dapuribuproject.R
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ProgressBar

class Home_Admin_Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment [cite: 1]
        return inflater.inflate(R.layout.fragment_home_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val container = view.findViewById<LinearLayout>(R.id.containerStatistik)

        val dataTrending = arrayOf(
            Triple("Rendang Sapi", "850 Klik", 95),
            Triple("Nasi Goreng", "620 Klik", 70),
            Triple("Pudding Coklat", "450 Klik", 50),
            Triple("Sayur Asem", "310 Klik", 35)
        )

        for (item in dataTrending) {
            val barView = layoutInflater.inflate(R.layout.item_statistik_bar, container, false)
            barView.findViewById<TextView>(R.id.tvNamaResepStat).text = item.first
            barView.findViewById<TextView>(R.id.tvJumlahKlik).text = item.second
            barView.findViewById<ProgressBar>(R.id.pbStatistik).progress = item.third

            container.addView(barView)
        }
    }

}