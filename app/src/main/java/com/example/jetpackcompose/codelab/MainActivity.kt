package com.example.jetpackcompose.codelab

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.InnerPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.jetpackcompose.codelab.ui.JetpackComposeCodelabTheme
import com.example.jetpackcompose.codelab.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeCodelabTheme { MainScreen() }
        }
    }

    @Composable
    private fun MainScreen() {
        Scaffold(
            topBar = {
                AppBar(title = getString(R.string.main_title)) { viewModel.refresh() }
            },
            bodyContent = {
                Greeting(viewModel.items.observeAsState(listOf()).value) {
                    viewModel.click(it)
                }
            }
        )
    }
}

@Composable
private fun AppBar(title: String, onMenuClick: () -> Unit = {}) {
    TopAppBar(
        title = { Text(text = title) },
        actions = {
            IconButton(onClick = onMenuClick) { Image(vectorResource(id = R.drawable.ic_refresh)) }
        }
    )
}

@Composable
private fun Greeting(items: List<String>, onClick: (Int) -> Unit = {}) {
    LazyColumnForIndexed(items, contentPadding = InnerPadding(8.dp)) { index, t ->
        val cardModifier = Modifier.fillParentMaxWidth().padding(6.dp).clickable {
            onClick(index)
        }
        Card(modifier = cardModifier) {
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
        Scaffold(
            topBar = { AppBar(title = "Preview") },
            bodyContent = { Greeting((0 until 10).map { it.toString() }) }
        )
    }
}