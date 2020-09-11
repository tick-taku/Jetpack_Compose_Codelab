package com.example.jetpackcompose.codelab

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
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
            JetpackComposeCodelabTheme {
                MainScreen()
                CongratulationDialog()
            }
        }
    }

    @Composable private fun MainScreen() {
        Scaffold(
            topBar = {
                AppBar(title = getString(R.string.main_title)) { viewModel.refresh() }
            },
            bodyContent = {
                NumberList(viewModel.items.observeAsState(listOf()).value) {
                    viewModel.click(it)
                }
            }
        )
    }

    @Composable private fun CongratulationDialog() {
        if (viewModel.openDialog.observeAsState(initial = false).value)
            AlertDialog(
                onDismissRequest = {},
                confirmButton = {
                    Button(onClick = { viewModel.refresh() }) {
                        Text(text = getString(R.string.ok))
                    }
                },
                title = { Text(text = getString(R.string.congratulations)) },
                text = { Text(text = getString(R.string.dialog_msg)) }
            )
    }
}

@Composable private fun AppBar(title: String, onMenuClick: () -> Unit = {}) {
    TopAppBar(
        title = { Text(text = title) },
        actions = {
            IconButton(onClick = onMenuClick) { Image(vectorResource(id = R.drawable.ic_refresh)) }
        }
    )
}

@Composable private fun NumberList(items: List<Boolean>, onClick: (Int) -> Unit = {}) {
    LazyColumnForIndexed(items, contentPadding = InnerPadding(8.dp)) { index, isChecked ->
        val cardModifier = Modifier.fillParentMaxWidth().padding(6.dp).clickable {
            onClick(index)
        }
        Card(modifier = cardModifier) {
            CheckCard(index = index, isChecked = isChecked)
        }
    }
}

@Composable private fun CheckCard(index: Int, isChecked: Boolean) {
    ConstraintLayout {
        val (text, checkbox) = createRefs()
        Text(
            text = if (isChecked) stringResource(R.string.check_mark) else index.toString(),
            textAlign = TextAlign.Start,
            modifier = Modifier.constrainAs(text) {
                width = Dimension.fillToConstraints
                start.linkTo(parent.start, margin = 30.dp)
                end.linkTo(checkbox.start, margin = 16.dp)
                top.linkTo(parent.top, margin = 16.dp)
                bottom.linkTo(parent.bottom, margin = 16.dp)
            }
        )
        Checkbox(
            checked = isChecked,
            enabled = false,
            onCheckedChange = {},
            modifier = Modifier.constrainAs(checkbox) {
                end.linkTo(parent.end, margin = 20.dp)
                centerVerticallyTo(parent)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable fun DefaultPreview() {
    JetpackComposeCodelabTheme {
        Scaffold(
            topBar = { AppBar(title = "Preview") },
            bodyContent = { NumberList(MutableList(10) { false }) }
        )
    }
}