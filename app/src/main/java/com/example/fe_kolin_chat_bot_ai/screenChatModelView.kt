package com.example.fe_kolin_chat_bot_ai

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class screenChatModelView : ViewModel() {

    val messageLis by lazy{
        mutableStateListOf<MessageModel>()
    }

    val generativeModel : GenerativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = Constant.apiKey
    )
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun sendMessage(question: String) {
        viewModelScope.launch {
            try {
                val chat = generativeModel.startChat(
                    history = messageLis.map{
                        content(it.role) {text(it.message )}
                    }.toList()
                )
                messageLis.add(MessageModel(question, "user")) // Thêm tin nhắn của người dùng
                messageLis.add(MessageModel("Typing.....", role ="model"))
                val response = chat.sendMessage(question)
                val geminiResponse = response.text ?: "Không có phản hồi từ Gemini."
                messageLis.removeLast()
                messageLis.add(MessageModel(geminiResponse, "model"))
                // Thêm phản hồi từ Gemini

                Log.i("Response gemini", geminiResponse)
            } catch (e: Exception) {
                Log.e("Gemini API", "Lỗi khi gửi tin nhắn: ${e.message}", e)
                messageLis.add(MessageModel("Đã xảy ra lỗi: ${e.message}", "error")) // Thêm thông báo lỗi
            }
        }
    }

}