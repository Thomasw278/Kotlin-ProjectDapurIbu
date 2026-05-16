package com.example.dapuribuproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.Fragment
import com.example.dapuribuproject.Adapter.ChatAdapter
import com.example.dapuribuproject.DataClass.ChatMessage
import com.example.dapuribuproject.Helper.DatabaseHelper
import com.example.dapuribuproject.R

class ChatFragment : Fragment() {
    private lateinit var dbHelper: DatabaseHelper
    private var isAdmin = false
    private var currentUsername = ""
    private var chatWithUser = "admin" // Default target chat

    private lateinit var rvChat: RecyclerView
    private lateinit var etMessage: EditText
    private lateinit var btnSend: ImageButton
    private lateinit var adapter: ChatAdapter
    private val chatList = mutableListOf<ChatMessage>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dbHelper = DatabaseHelper(requireContext())
        
        currentUsername = activity?.intent?.getStringExtra("username") ?: "User"
        isAdmin = activity?.intent?.getBooleanExtra("isAdmin", false) ?: false

        // Ambil Target user Dari Argument
        arguments?.getString("chatWith")?.let {
            chatWithUser = it
        }

        return if (isAdmin && chatWithUser == "admin") {
            inflater.inflate(R.layout.fragment_chat_admin, container, false)
        } else {
            inflater.inflate(R.layout.fragment_chat, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        if (isAdmin && chatWithUser == "admin") {
            setupAdminInbox(view)
        } else {
            setupChatRoom(view)
        }
    }

    private fun setupAdminInbox(view: View) {
        val container = view.findViewById<LinearLayout>(R.id.rvUserChatList)
        container?.removeAllViews()
        
        val listCustomer = dbHelper.getChatUsersForAdmin()

        if (listCustomer.isEmpty()) {
            val emptyText = TextView(context).apply {
                text = "Belum ada pesan masuk."
                setPadding(20, 50, 20, 20)
                gravity = android.view.Gravity.CENTER
            }
            container?.addView(emptyText)
        }

        for (user in listCustomer) {
            val itemView = layoutInflater.inflate(R.layout.item_user_chat, null)
            itemView.findViewById<TextView>(R.id.tvChatUserName).text = user.first
            itemView.findViewById<TextView>(R.id.tvLastMessage).text = user.second
            
            itemView.setOnClickListener {
                val fragment = ChatFragment()
                val bundle = Bundle()
                bundle.putString("chatWith", user.first)
                fragment.arguments = bundle
                
                parentFragmentManager.beginTransaction()
                    .replace(R.id.FrameLayout, fragment)
                    .addToBackStack(null)
                    .commit()
            }
            container?.addView(itemView)
        }
    }

    private fun setupChatRoom(view: View) {
        val header = view.findViewById<TextView>(R.id.tvHeaderChat)
        
        // Identitas pengirim di database
        val myChatId = if (isAdmin) "admin" else currentUsername
        
        if (isAdmin) {
            header.text = "Membalas: $chatWithUser"
        } else {
            header.text = "Chat Admin"
        }

        rvChat = view.findViewById(R.id.rvChat)
        etMessage = view.findViewById(R.id.etMessage)
        btnSend = view.findViewById(R.id.btnSend)

        adapter = ChatAdapter(chatList)
        rvChat.layoutManager = LinearLayoutManager(context)
        rvChat.adapter = adapter

        loadMessages(myChatId)

        btnSend.setOnClickListener {
            val message = etMessage.text.toString().trim()
            if (message.isNotEmpty()) {
                dbHelper.insertPesan(myChatId, chatWithUser, message)
                etMessage.setText("")
                loadMessages(myChatId)
            }
        }
    }

    private fun loadMessages(myChatId: String) {
        chatList.clear()
        val messages = dbHelper.getChatMessages(myChatId, chatWithUser)
        chatList.addAll(messages)
        adapter.notifyDataSetChanged()
        if (chatList.isNotEmpty()) {
            rvChat.scrollToPosition(chatList.size - 1)
        }
    }
}
