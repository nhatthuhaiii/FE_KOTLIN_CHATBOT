package com.example.fe_kolin_chat_bot_ai

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class screenChatModelView : ViewModel() {

    val messageLis = mutableStateListOf<MessageModel>()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = Constant.apiKey
    )

    private val dbRef by lazy {
        Firebase.database.reference
    }

    init {
        try {

            Log.d("FirebaseInit", "Database reference initialized")
        } catch (e: Exception) {
            Log.e("FirebaseInit", "Firebase chưa được khởi tạo: ${e.message}")
        }
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun sendMessage(question: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val chat = generativeModel.startChat(
                    history = messageLis.map {
                        content(it.role) { text(it.message) }
                    }
                )
                messageLis.add(MessageModel(question, "user"))

                dbRef?.child("message")?.child("user111")?.push()?.setValue(MessageModel(question, "user"))
                    ?: Log.w("Firebase", "Không thể ghi dữ liệu: dbRef null")

                messageLis.add(MessageModel("Typing.....", "model"))

                val response = chat.sendMessage(question)
                val geminiResponse = response.text ?: "Không có phản hồi từ Gemini."

                messageLis.removeLast()
                messageLis.add(MessageModel(geminiResponse, "model"))

                dbRef?.child("message")?.child("user111")?.push()?.setValue(MessageModel(geminiResponse, "model"))
                    ?: Log.w("Firebase", "Không thể ghi dữ liệu: dbRef null")

                Log.i("Gemini API", "Response: $geminiResponse")

            } catch (e: Exception) {
                Log.e("Gemini API", "Lỗi: ${e.message}", e)
                messageLis.add(MessageModel("Đã xảy ra lỗi: ${e.message}", "error"))
            }
        }
    }
}
