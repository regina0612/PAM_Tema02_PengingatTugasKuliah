package ui.form

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
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

// Layar form untuk menambahkan tugas baru. Mengambil state dari TaskFormViewModel.
@Composable
fun TaskFormScreen() {
    // Ambil instance TaskFormViewModel. TaskRepository dibuat langsung di sini.
    val viewModel = viewModel { TaskFormViewModel(TaskRepository()) }

    // Kumpulkan uiState sebagai State Compose, sehingga layar merekomposisi saat state berubah
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Judul halaman
        Text(
            text = "Form Tambah Tugas",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        // Input field untuk Judul tugas
        CustomTextField(
            value = uiState.judul,
            onValueChange = { viewModel.updateJudul(it) },
            label = "Judul Tugas"
        )

        // Input field untuk Nama Mata Kuliah
        CustomTextField(
            value = uiState.mataKuliah,
            onValueChange = { viewModel.updateMataKuliah(it) },
            label = "Nama Mata Kuliah"
        )

        // Input field untuk Deadline pengumpulan tugas
        CustomTextField(
            value = uiState.deadline,
            onValueChange = { viewModel.updateDeadline(it) },
            label = "Deadline (misal: 25 Okt 2026)"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tombol Simpan Tugas. enabled tergantung uiState.bisaSimpan (semua field terisi).
        // Saat diklik, memanggil fungsi simpan() dari ViewModel.
        Button(
            onClick = {
                viewModel.simpan()
            },
            enabled = uiState.bisaSimpan,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan Tugas")
        }
    }
}
