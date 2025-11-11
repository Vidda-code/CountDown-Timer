package com.example.countdowntimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.countdowntimer.ui.theme.CountDownTimerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CountDownTimerTheme {
                TimerScreen()
            }
        }
    }
}

@Composable
fun TimerScreen(viewModel: TimerViewModel = viewModel()) {
    val timeHold = viewModel.time.value
    val isRunningHold = viewModel.isRunning.value

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Display time
        Text(
            text = viewModel.formatTime(timeHold),
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Control buttons
        Row {
            Button(onClick = { viewModel.setTime(120000L) }) {
                Text("Set Time")
            }

            Button(
                onClick = {
                    if (isRunningHold) viewModel.pauseTimer()
                    else viewModel.startTimer(timeHold)
                }
            ) {
                Text(if (isRunningHold) "Pause" else "Start")
            }

            Button(onClick = { viewModel.resetTimer() }) {
                Text("Reset")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CountDownTimerTheme {
        TimerScreen()
    }
}