package io.github.takusan23.textfieldcursorpos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.takusan23.textfieldcursorpos.ui.theme.TextFieldCursorPosTheme

class MainActivity : ComponentActivity() {

    private val ALL_LIST = listOf(
        "Android 12",
        "Android 13 DP",
        "Android JetpackCompose",
        "Android Room",
        "Android LiveData",
        "Android ViewModel",
    )

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextFieldCursorPosTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    // サジェスト一覧
                    val suggestList = remember { mutableStateListOf<String>() }
                    // 入力欄の文字
                    val textFieldValue = remember { mutableStateOf(TextFieldValue()) }
                    // 文字だけ変更検知
                    LaunchedEffect(key1 = textFieldValue.value.text, block = {
                        suggestList.clear()
                        suggestList.addAll(ALL_LIST.filter { it.contains(textFieldValue.value.text) })
                    })
                    Column(modifier = Modifier.padding(10.dp)) {
                        OutlinedTextField(
                            value = textFieldValue.value,
                            onValueChange = { textFieldValue.value = it }
                        )
                        LazyColumn(content = {
                            items(suggestList) { suggestText ->
                                Surface(
                                    onClick = {
                                        textFieldValue.value = textFieldValue.value.copy(
                                            text = suggestText,
                                            selection = TextRange(suggestText.length), // 押したらカーソルを最後に移動
                                        )
                                    }
                                ) { Text(modifier = Modifier.padding(10.dp), text = suggestText) }
                            }
                        })
                    }
                }
            }
        }
    }
}