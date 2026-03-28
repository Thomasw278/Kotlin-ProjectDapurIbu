package com.example.dapuribuproject.fragment

import ChatAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dapuribuproject.ChatMessage
import com.example.dapuribuproject.R

class Customer : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        val rvChat = view.findViewById<RecyclerView>(R.id.rvChat)

        // Data Contoh
        val chatList = mutableListOf(
            ChatMessage("Halo! Ada yang bisa Chef bantu?", false),
            ChatMessage("Chef, bumbu halusnya perlu ditumis dulu?", true),
            ChatMessage("Iya betul, tumis sampai harum ya!", false)
        )

        val adapter = ChatAdapter(chatList)
        rvChat.layoutManager = LinearLayoutManager(context)
        rvChat.adapter = adapter

        return view
    }
}