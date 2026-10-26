
package data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import model.Course
import model.Task
import model.TaskStatus

class TaskRepository : Sumber<Task> {

    // Menyimpan daftar tugas yang bisa berubah.
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())

    // Daftar tugas yang bisa diamati oleh layar.
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    // Menambahkan tugas baru ke daftar.
    fun addTask(task: Task) {
        _tasks.value = _tasks.value + task
    }

    // Membuat contoh data tugas.
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

    // Mengambil data tugas berdasarkan ID.
    override suspend fun ambil(id: String): Task {
        delay(1000)
        return contohTask(id)
    }

    // Memantau perubahan status tugas secara bertahap.
    override fun pantau(id: String): Flow<Task> = flow {
        val dasar = contohTask(id)

        emit(dasar)
        delay(1500)

        emit(dasar.copy(status = TaskStatus.InProgress))
        delay(2000)

        emit(dasar.copy(status = TaskStatus.Completed))
    }
}
