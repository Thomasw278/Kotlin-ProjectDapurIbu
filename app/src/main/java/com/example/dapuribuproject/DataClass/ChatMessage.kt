package com.example.dapuribuproject.DataClass

data class ChatMessage(
    val id: Int = 0,
    val sender: String,
    val receiver: String,
    val message: String,
    val timestamp: String = "",
    val isSentByMe: Boolean = false
)