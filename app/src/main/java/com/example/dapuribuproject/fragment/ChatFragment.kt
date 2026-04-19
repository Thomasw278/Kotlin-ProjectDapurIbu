package com.example.dapuribuproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.dapuribuproject.R

class ChatFragment : Fragment() {
    private val isAdmin = false 

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (isAdmin) {
            inflater.inflate(R.layout.fragment_chat_admin, container, false)
        } else {
            inflater.inflate(R.layout.fragment_chat, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isAdmin) {
            setupAdminUI(view)
        } else {
            setupCustomerUI(view)
        }
    }

    private fun setupAdminUI(view: View) {
        val container = view.findViewById<LinearLayout>(R.id.rvUserChatList)
        
        val listCustomer = arrayOf(
            Pair("Siti Aminah", "Izin tanya resep pudingnya chef..."),
            Pair("Budi Santoso", "Terima kasih infonya!"),
            Pair("Rina Wati", "Bumbu halusnya apa saja ya?")
        )

        for (user in listCustomer) {
            val itemView = layoutInflater.inflate(R.layout.item_user_chat, null)
            itemView.findViewById<TextView>(R.id.tvChatUserName).text = user.first
            itemView.findViewById<TextView>(R.id.tvLastMessage).text = user.second
            
            container?.addView(itemView)
        }
    }

    private fun setupCustomerUI(view: View) {
        // Logika untuk Customer (misal inisialisasi list chat atau pesan otomatis)
        // Di fragment_chat.xml ada RecyclerView dengan id rvChat
    }
}
