package com.example.fe_kolin_chat_bot_ai

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import com.example.fe_kolin_chat_bot_ai.ui.theme.FE_Kolin_Chat_Bot_AITheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val chatViewModel = ViewModelProvider(this)[screenChatModelView::class.java]
        setContent {
            FE_Kolin_Chat_Bot_AITheme {
                Scaffold(modifier = Modifier.fillMaxSize(),

                ) { innerPadding ->
                    Chat(modifier = Modifier.padding(innerPadding),chatViewModel)
                }
            }
        }
    }
}

