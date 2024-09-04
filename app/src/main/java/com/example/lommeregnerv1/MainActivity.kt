package com.example.lommeregnerv1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.lommeregnerv1.ui.theme.Lommeregnerv1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lommeregnerv1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    regner(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun regnerv2() {
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
        bottomBar = { MyButtonBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        regner(modifier = Modifier.padding(innerPadding))
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
fun regner(modifier: Modifier = Modifier) {
    var numberStr1 by remember { mutableStateOf("") }
    var numberStr2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var errorStateInfo by remember { mutableStateOf("") }

    Column(modifier = modifier) {


    OutlinedTextField(value = numberStr1, onValueChange ={
                      numberStr1 =it
        errorStateInfo = "" },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = { Text("Enter a number") },

    )
     if (numberStr1.isEmpty()) {
           errorStateInfo= "Please enter a number"
        }else{
            errorStateInfo = ""
        }

    OutlinedTextField(value = numberStr2, onValueChange ={numberStr2 =it},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = { Text("Enter a number") })

    Row {
        Button(onClick = { result = (numberStr1.toInt() + numberStr2.toInt()).toString() }) {
            Text(text = "+")
        }
        Button(onClick = { result = (numberStr1.toInt() - numberStr2.toInt()).toString() }) {
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