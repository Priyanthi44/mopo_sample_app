package com.mopo.sample

import MainViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mopo.sample.data.DataItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val state = viewModel.uiState.collectAsState()
    val syncState = viewModel.syncState.collectAsState()
    val isOnline = viewModel.isOnline.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("MOPO Offline Demo") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = if (isOnline.value == true) Icons.Default.Cloud else Icons.Default.CloudOff,
                contentDescription = "Offline Mode",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (isOnline.value == true) "Online Sync Status" else "Offline Sync Status",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (state.value.toString().contains("Loading")) Color.Yellow.copy(alpha = 0.2f)
                    else if (state.value.toString().contains("Success")|| syncState.value.toString().contains("Success")) Color.Green.copy(alpha = 0.2f)
                    else Color.Red.copy(alpha = 0.2f)
                )
            ) {
                Text(
                    text = "Status: ${state.value}  ${syncState.value}",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(24.dp))



            Button(
                onClick = {
                    if (isOnline.value == true) viewModel.uploadItem("Test item",  "") else viewModel.saveItem(DataItem(0, "Test item",  "", false))
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = if (isOnline.value == true) Icons.Default.Cloud else Icons.Default.CloudOff,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isOnline.value == true) "Save & Upload" else "Save Offline",
                    fontSize = 18.sp
                )
            }


            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = if (isOnline.value == true) "Data will be uploaded and synced while online" else "Data will be saved locally and synced when back online.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}
