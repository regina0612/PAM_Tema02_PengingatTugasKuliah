
package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import data.TaskRepository
import tampilan.TaskListScreen

@Composable
fun LayarUtama(
    onKembali: () -> Unit = {}
) {
    // Mengambil daftar tugas dari repository.
    val repository = remember { TaskRepository() }
    val tasks by repository.tasks.collectAsState()

    // Layar daftar mengikuti perubahan daftar tugas.
    TaskListScreen(
        tasks = tasks,
        onKembali = onKembali
    )
}
