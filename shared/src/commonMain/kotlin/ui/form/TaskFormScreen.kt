
package ui.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import data.TaskRepository
import ui.components.CustomTextField

@Composable
fun TaskFormScreen(
    repository: TaskRepository,
    onSelesai: () -> Unit = {}
) {
    val viewModel = viewModel {
        TaskFormViewModel(repository)
    }

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Form Tambah Tugas",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        CustomTextField(
            value = uiState.judul,
            onValueChange = { viewModel.updateJudul(it) },
            label = "Judul Tugas"
        )

        CustomTextField(
            value = uiState.mataKuliah,
            onValueChange = { viewModel.updateMataKuliah(it) },
            label = "Nama Mata Kuliah"
        )

        CustomTextField(
            value = uiState.deadline,
            onValueChange = { viewModel.updateDeadline(it) },
            label = "Deadline (misal: 25 Okt 2026)"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.simpan()
                onSelesai()
            },
            enabled = uiState.bisaSimpan,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan Tugas")
        }
    }
}
