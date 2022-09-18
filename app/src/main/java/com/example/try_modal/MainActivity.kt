package com.example.try_modal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.try_modal.ui.theme.Try_modalTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val drawerState = rememberBottomDrawerState(
                initialValue = BottomDrawerValue.Closed,
                confirmStateChange = {
                    it != BottomDrawerValue.Open
                }
            )
            val scope = rememberCoroutineScope()
            val drawerContentType = remember { mutableStateOf(DrawerContentType.ONE) }
            CustomModalScreen(
                drawerContent = {
                    when (drawerContentType.value) {
                        DrawerContentType.ONE -> {
                            (1..100).forEach {
                                Button(onClick = {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }) {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(32.dp), text = "TextONE $it"
                                    )
                                }
                            }
                        }
                        DrawerContentType.TWO -> {
                            (1..100).forEach {
                                Button(onClick = {
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }) {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(32.dp), text = "TextTWO $it"
                                    )
                                }
                            }
                        }
                    }
                },
                content = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(modifier = Modifier.align(Alignment.BottomEnd)) {
                            Button(
                                onClick = {
                                    scope.launch {
                                        drawerContentType.value = DrawerContentType.ONE
                                        drawerState.expand()
                                    }
                                }
                            ) {
                                Text(text = "OpenOne")
                            }
                            Button(
                                onClick = {
                                    scope.launch {
                                        drawerContentType.value = DrawerContentType.TWO
                                        drawerState.expand()
                                    }
                                }
                            ) {
                                Text(text = "OpenTwo")
                            }
                        }
                    }
                },
                drawerState = drawerState
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomModalScreen(
    drawerState: BottomDrawerState,
    drawerContent: @Composable ColumnScope.() -> Unit,
    content: @Composable () -> Unit,
) {
    BottomDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .verticalScroll(rememberScrollState())
            ) {
                drawerContent()
            }
        },
    ) {
        content()
    }
}

enum class DrawerContentType {
    ONE,
    TWO
}
