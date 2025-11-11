package com.example.countdowntimer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {

    private var timerJob: Job? = null

    private val _time = mutableStateOf(0L)
    val time: State<Long> = _time

    private val _isRunning = mutableStateOf(false)
    val isRunning: State<Boolean> = _isRunning

    fun setTime(durationMillis: Long) {
        if (!_isRunning.value) {
            _time.value = durationMillis
        }
    }

    fun startTimer(durationMillis: Long) {
        if (_isRunning.value || durationMillis == 0L) return

        _time.value = durationMillis
        _isRunning.value = true

        timerJob = viewModelScope.launch {
            val startTime = System.currentTimeMillis()
            val endTime = startTime + durationMillis

            while (_time.value > 0 && _isRunning.value) {
                delay(100)
                _time.value = (endTime - System.currentTimeMillis()).coerceAtLeast(0)
            }

            if (_time.value == 0L) {
                _isRunning.value = false
            }
        }

    }

    fun pauseTimer() {
        _isRunning.value = false
        timerJob?.cancel()
    }

    fun resetTimer() {
        pauseTimer()
        _time.value = 0L
    }

    fun formatTime(millis: Long): String {
        val totalSeconds = millis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "%02d:%02d".format(minutes, seconds)
    }
}