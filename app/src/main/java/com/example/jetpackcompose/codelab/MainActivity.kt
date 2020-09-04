package com.example.jetpackcompose.codelab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.InnerPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.jetpackcompose.codelab.ui.JetpackComposeCodelabTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeCodelabTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting((0 until 100).map { it.toString() })
                }
            }
        }
    }
}

@Composable
fun Greeting(text: List<String>) {
    val state = text.toMutableStateList()
    LazyColumnForIndexed(state, contentPadding = InnerPadding(8.dp)) { index, t ->
        Card(modifier = Modifier.fillParentMaxWidth().padding(6.dp).clickable {
            state[index] = "Clicked!"
        }) {
            Text(text = t,
                 textAlign = TextAlign.Start,
                 modifier = Modifier.fillMaxWidth().padding(30.dp, 16.dp, 16.dp, 16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComposeCodelabTheme {
        Greeting((0 until 10).map { it.toString() })
    }
}