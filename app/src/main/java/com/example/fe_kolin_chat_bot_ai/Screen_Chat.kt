package com.example.fe_kolin_chat_bot_ai

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.nio.file.WatchEvent

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun Chat(modifier: Modifier,viewmodel: screenChatModelView){
    Column(
        modifier
    ) {
        AppBar()
        MessageList(Modifier
            .weight(1f)
            .padding(horizontal = 8.dp),viewmodel.messageLis)
        MessageInput(
            onSendMessage = {
                viewmodel.sendMessage(it)
            }
        )
    }
}

@Composable
fun AppBar(){
    Box(
      modifier = Modifier
          .fillMaxWidth()
          .background(MaterialTheme.colorScheme.primary)
    ){
        Text("CHAT BOX ", modifier = Modifier.padding(16.dp),
                color = Color.White,
                fontSize = 20.sp
            )
    }


}
@Composable
fun MessageInput(onSendMessage:(String)->Unit){
    var item  by remember {
        mutableStateOf("")
    }
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically

    ){
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = item,
            onValueChange = {item = it}
        )
        IconButton(onClick = {

            onSendMessage(item)
            item = ""
        }) {

            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "send",
                tint = Color.White

            )

        }
    }
}

@Composable
fun MessageList(modifier: Modifier, messageList:List<MessageModel>){


    if(messageList.isEmpty()){
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.Email,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(50.dp)
                )
                Text("Ask me Anything", fontSize = 22.sp)
            }
        }
        }

    else{
    LazyColumn (
        modifier,
        userScrollEnabled = true

    ){
        items(messageList){
           MessageRow(it)
        }

    }
    }

}
@Composable
fun MessageRow(messageModel: MessageModel){
    val isModel = messageModel.role=="model"
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box (modifier = Modifier
                .align(
                    if (isModel)
                        Alignment.BottomStart else {
                        Alignment.BottomEnd
                    }
                )
                .padding(
                    start = if (isModel) 8.dp else 70.dp,
                    end = if (isModel) 70.dp else 8.dp,
                    bottom = 4.dp,
                    top = 4.dp,


                    )
                .clip(RoundedCornerShape(20.dp))
                .background(if (isModel) Color.Blue else Color.Green.copy(0.6f))
                .padding(10.dp)

                )
            {
                Text(text = messageModel.message, fontWeight = FontWeight.W500, color = Color.White)
            }
        }
    }
}


