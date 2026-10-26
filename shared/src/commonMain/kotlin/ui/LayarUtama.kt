
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
    val repository = remember { TaskRepository() }
    val tasks by repository.tasks.collectAsState()

    TaskListScreen(
        tasks = tasks,
        repository = repository,
        onKembali = onKembali
    )
}
