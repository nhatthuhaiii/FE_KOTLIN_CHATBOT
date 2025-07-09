package com.example.fe_kolin_chat_bot_ai

data class MessageModel(
    val message:String,
    val role:String,
    val createdAt:Long = System.currentTimeMillis()
)
