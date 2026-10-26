
package tampilan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.TaskRepository
import model.Task
import model.TaskStatus
import ui.components.TaskItemCard
import ui.form.TaskFormScreen

@Composable
fun TaskListScreen(
    tasks: List<Task>,
    repository: TaskRepository,
    onKembali: () -> Unit = {}
) {
    var kataKunci by remember { mutableStateOf("") }
    var filterStatus by remember { mutableStateOf("Semua") }
    var tampilFormTambah by remember { mutableStateOf(false) }

    val hijau = Color(0xFF567568)
    val krem = Color(0xFFF6F3EA)

    // Filter berdasarkan judul dan status tugas.
    val tugasTerfilter = tasks.filter { tugas ->
        val cocokJudul = tugas.title.contains(
            kataKunci,
            ignoreCase = true
        )

        val cocokStatus = when (filterStatus) {
            "Belum Selesai" ->
                tugas.status == TaskStatus.Pending

            "Sedang Dikerjakan" ->
                tugas.status == TaskStatus.InProgress

            "Selesai" ->
                tugas.status == TaskStatus.Completed

            else -> true
        }

        cocokJudul && cocokStatus
    }

    // Jika FAB ditekan, tampilkan form tambah tugas.
    if (tampilFormTambah) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextButton(
                onClick = { tampilFormTambah = false }
            ) {
                Text(
                    text = "← Kembali ke Daftar Tugas",
                    color = hijau
                )
            }

            TaskFormScreen(
                repository = repository,
                onSelesai = {
                    tampilFormTambah = false
                }
            )
        }
    } else {
        Scaffold(
            containerColor = krem,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { tampilFormTambah = true },
                    containerColor = hijau,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(16.dp),
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Text(
                        text = "+",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                TextButton(
                    onClick = onKembali,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "← Kembali ke TemanTugas",
                        color = hijau,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Text(
                    text = "Daftar Tugas Kuliah",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = hijau,
                    modifier = Modifier.padding(16.dp)
                )

                TextField(
                    value = kataKunci,
                    onValueChange = { kataKunci = it },
                    label = { Text("Cari tugas...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    listOf(
                        "Semua",
                        "Belum Selesai",
                        "Sedang Dikerjakan",
                        "Selesai"
                    ).forEach { status ->
                        FilterChip(
                            selected = filterStatus == status,
                            onClick = { filterStatus = status },
                            label = { Text(status) }
                        )
                    }
                }

                Text(
                    text = "${tugasTerfilter.size} tugas ditemukan",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )

                if (tugasTerfilter.isEmpty()) {
                    Text(
                        text = "Belum ada tugas yang sesuai. Tekan tombol + untuk menambahkan tugas.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 80.dp
                        )
                    ) {
                        items(
                            items = tugasTerfilter,
                            key = { it.id }
                        ) { tugas ->
                            TaskItemCard(task = tugas)
                        }
                    }
                }
            }
        }
    }
}
