package dev.tomoya0x00.mutablestatetest

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class TimelineUiState(
    val isLoading: Boolean = false,
    val tweets: List<String> = emptyList()
)

class TimelineViewModel(): ViewModel() {
    var uiState by mutableStateOf(TimelineUiState())
        private set

    fun refreshTimeline() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            delay(10) // instead of calling a real api
            uiState = uiState.copy(
                isLoading = false,
                tweets = listOf("Android 13 Tiramisu", "Android 12L")
            )
        }
    }
}