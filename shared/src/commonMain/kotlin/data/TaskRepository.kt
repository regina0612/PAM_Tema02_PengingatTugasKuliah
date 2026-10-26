package data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import model.Course
import model.Task
import model.TaskStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TaskRepository : Sumber<Task> {
    // Menyimpan daftar tugas yang bisa berubah.
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())

    // Daftar tugas yang bisa diamati oleh layar.
    val tasks: StateFlow<List<Task>> = _tasks

    // Menambahkan tugas baru ke daftar.
    fun addTask(task: Task) {
        _tasks.value = _tasks.value + task
    }
    private fun contohTask(id: String) = Task(
        id = id,
        title = "Laporan Pengembangan Aplikasi Mobile",
        course = Course(
            id = "MK-01",
            name = "Pengembangan Aplikasi Mobile",
            code = "IF25-22017"
        ),
        deadline = "25 Okt 2026",
        status = TaskStatus.Pending,
        description = null
    )

    override suspend fun ambil(id: String): Task {
        delay(1000) // pura-pura menunggu server
        return contohTask(id)
    }

    override fun pantau(id: String): Flow<Task> = flow {
        val dasar = contohTask(id)
        emit(dasar)                                         // Pending
        delay(1500)
        emit(dasar.copy(status = TaskStatus.InProgress))    // InProgress
        delay(2000)
        emit(dasar.copy(status = TaskStatus.Completed))     // Completed
    }
}