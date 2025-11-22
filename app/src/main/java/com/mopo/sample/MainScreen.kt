package com.mopo.sample

import androidx.compose.foundation.layout.Column

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val state = viewModel.uiState.collectAsState()
    Column {
        Text(text = "MOPO Offline Demo")
        Text(text = "Status: ${state.value}")
        Button(onClick = { /* call viewModel.saveItem(...) */ }) {
            Text("Save (demo)")
        }
    }
}
