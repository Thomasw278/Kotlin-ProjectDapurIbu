package com.example.dapuribuproject.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.dapuribuproject.R
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Admin : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chat_admin, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val container = view.findViewById<LinearLayout>(R.id.rvUserChatList)

        val listCustomer = arrayOf(
            Pair("Pak Eko Verianto", "Izin tanya resep pudingnya chef"),
            Pair("Pak Budi Santoso", "Terima kasih infonya! chef Wisnu"),
            Pair("Pak Willy Sudiarto", "Bumbu halusnya apa saja ya? chef Wisnu")
        )

        for (user in listCustomer) {
            val itemView = layoutInflater.inflate(R.layout.item_user_chat, null)
            itemView.findViewById<TextView>(R.id.tvChatUserName).text = user.first
            itemView.findViewById<TextView>(R.id.tvLastMessage).text = user.second

             container.addView(itemView)
        }
    }

}