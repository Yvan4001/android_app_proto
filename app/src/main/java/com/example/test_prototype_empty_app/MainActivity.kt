package com.example.test_prototype_empty_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.test_prototype_empty_app.ui.theme.Test_Prototype_empty_appTheme
import android.widget.Button
import android.widget.TextView
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class MainActivity : ComponentActivity() {
    private lateinit var daoFactory: DaoFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        daoFactory = DaoFactory()
        daoFactory.setupDatabase()

        setContent {
            Test_Prototype_empty_appTheme {
                MainScreen(daoFactory)
            }
        }
    }
}

@Composable
fun MainScreen(daoFactory: DaoFactory) {
    var message = remember { mutableStateOf("No message yet") }

    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            Button(onClick = {
                daoFactory.addMessage("New message")
                message.value = "New message added"
            }) {
                Text("Add Message")
            }
            Text(text = message.value, modifier = Modifier.padding(top = 16.dp))
        }
    }
}
