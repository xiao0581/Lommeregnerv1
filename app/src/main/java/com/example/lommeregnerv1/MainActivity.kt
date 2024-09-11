package com.example.lommeregnerv1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lommeregnerv1.ui.theme.Lommeregnerv1Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lommeregnerv1Theme {
                regnerv2()
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun regnerv2() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(

        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Calculator")
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Add")
                    }
                },


            )
        },

        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
                snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        regner(
            modifier = Modifier.padding(innerPadding),
            snackbarHostState = snackbarHostState,
            scope = scope

        )
    }

    }

@Composable
fun MyButtonBar() {
    // https://developer.android.com/develop/ui/compose/components/app-bars#bottom
    BottomAppBar(
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(Icons.Filled.Check, contentDescription = "Localized description")
            }
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "Localized description",
                )
            }

        },
        // Generally, do not use two floating action buttons on the same screen.
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* do something */ },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, "Localized description")
            }
        }
    )
}


@Composable
fun regner(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState, // 传入 snackbarHostState
    scope: CoroutineScope
)
{

    var numberStr1 by remember { mutableStateOf("") }
    var numberStr2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var numberStr1Error by remember { mutableStateOf(false) }
    var numberStr2Error by remember { mutableStateOf(false) }

    var numberStr1Touched by remember { mutableStateOf(false) }


    Column(modifier = modifier.padding(16.dp)) {

        OutlinedTextField(
            value = numberStr1,
            onValueChange = {
                numberStr1 = it

                    numberStr1Error = it.isEmpty()


            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Enter a number") },
            isError = numberStr1Error,
            supportingText = {
                if (numberStr1Error) {
                    Text("invalid input", color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.padding(bottom = 8.dp)
                .onFocusChanged {
                     focusState ->
                    if (focusState.isFocused) {
                        numberStr1Touched = false
                    }
                }
        )

    OutlinedTextField(value = numberStr2,
        onValueChange ={
            numberStr2 =it
            numberStr2Error = it.isEmpty() },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = { Text("Enter a number") },
        isError = numberStr2Error,
        supportingText = {
            if (numberStr2Error) {
                Text("invalid input", color = MaterialTheme.colorScheme.error)
            }
        },)

    Row {
        Button(
            onClick = {
                if (numberStr1.isEmpty() || numberStr2.isEmpty()) {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            "Please enter both numbers",
                            duration = SnackbarDuration.Short
                        )
                    }
                } else {
                    result = (numberStr1.toInt() + numberStr2.toInt()).toString()
                }
            },
            enabled = numberStr1.isNotEmpty() && numberStr2.isNotEmpty()

        ) {
            Text(text = "+")
        }
        Button(onClick = { if (numberStr1.isEmpty() || numberStr2.isEmpty()) {
            // 如果其中一个输入为空，显示 Snackbar
            scope.launch {
                snackbarHostState.showSnackbar(
                    "Please enter both numbers", // 错误提示信息
                    duration = SnackbarDuration.Short
                )
            }
        } else {
            result = (numberStr1.toInt() - numberStr2.toInt()).toString()
        } }) {
            Text(text = "-")
        }
        Button(onClick = { result = (numberStr1.toInt() * numberStr2.toInt()).toString() }) {
            Text(text = "*")
        }
        Button(onClick = { result = (numberStr1.toInt() / numberStr2.toInt()).toString() }) {
            Text(text = "/")
        }
    }
        Text(text = "Result: $result")




    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Lommeregnerv1Theme {
        regnerv2()
    }
}