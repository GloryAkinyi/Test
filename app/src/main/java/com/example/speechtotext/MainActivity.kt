package com.example.speechtotext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.speechtotext.ui.theme.SpeechToTextTheme
import java.util.*

class MainActivity : ComponentActivity() {

    var outputTxt by mutableStateOf("Click button for Speech text ")

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeechToTextTheme {

                Surface(color = MaterialTheme.colorScheme.background) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "Speech to Text",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                })
                        }) {
                        SpeechToText()
                    }
                }
            }
        }
    }
    @Composable
    fun SpeechToText() {
        val context = LocalContext.current
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Speech to Text Example",
                modifier = Modifier.fillMaxWidth().padding(15.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp, pressedElevation = 0.dp, disabledElevation = 0.dp
                ),
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                onClick = { getSpeechInput(context = context) },
            ) {
                Icon(
                    imageVector = Icons.Filled.Mic,
                    contentDescription = "Mic",
                    tint = Color.Green,
                    modifier = Modifier.height(100.dp).width(100.dp).padding(5.dp)
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = outputTxt,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
    private fun getSpeechInput(context: Context) {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            Toast.makeText(context, "Speech not Available", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Something")
            startActivityForResult(intent, 101)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            outputTxt = result?.get(0).toString()
        }
    }
}
