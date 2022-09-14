package com.haeseong.composetest

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haeseong.composetest.ui.theme.ComposeTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
                Conversation(msg = SampleData.conversationSample)
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(40.dp)    // 사이즈
                .clip(CircleShape) // 이미지 모양
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        // 이미지와 컬럼 사이의 수평공간
        Spacer(modifier = Modifier.width(8.dp))

        // We keep track if the message is expanded or not in this
        // 메시지가 확장되었는지 여부를 추적
        var isExpanded by remember {
            mutableStateOf(false)
        }

        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.subtitle2
            )
            // 텍스트 Column 간의 높이 간격
            Spacer(modifier = Modifier.height(4.dp))
            Surface(shape = MaterialTheme.shapes.medium, elevation = 1.dp) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2,
                    )
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewMessageCard() {
    ComposeTestTheme() {
        Surface {
            MessageCard(msg = Message("Colleague", "Hey, take a look at Jetpack Compose!!"))
        }
    }
}

@Composable
fun Conversation(msg: List<Message>) {
    LazyColumn {
        items(msg) { msg ->
            MessageCard(msg = msg)
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    ComposeTestTheme {
        Conversation(SampleData.conversationSample)
    }
}