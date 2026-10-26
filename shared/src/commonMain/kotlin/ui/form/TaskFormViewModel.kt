package ui.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import model.Course
import model.Task
import model.TaskStatus

// State layar form tambah tugas
data class TaskFormUiState(
    val judul: String = "",
    val mataKuliah: String = "",
    val deadline: String = "",
    val bisaSimpan: Boolean = false
)

// ViewModel untuk layar form tugas. Mengelola state input dan logika simpan.
class TaskFormViewModel(private val repository: TaskRepository) : ViewModel() {

    // State yang diexpose ke UI sebagai StateFlow
    private val _uiState = MutableStateFlow(TaskFormUiState())
    val uiState: StateFlow<TaskFormUiState> = _uiState.asStateFlow()

    // Dipanggil saat user mengetik di field Judul
    fun updateJudul(value: String) {
        _uiState.value = _uiState.value.copy(
            judul = value,
            bisaSimpan = hitungBisaSimpan(judul = value, mataKuliah = _uiState.value.mataKuliah, deadline = _uiState.value.deadline)
        )
    }

    // Dipanggil saat user mengetik di field Nama Mata Kuliah
    fun updateMataKuliah(value: String) {
        _uiState.value = _uiState.value.copy(
            mataKuliah = value,
            bisaSimpan = hitungBisaSimpan(judul = _uiState.value.judul, mataKuliah = value, deadline = _uiState.value.deadline)
        )
    }

    // Dipanggil saat user mengetik di field Deadline
    fun updateDeadline(value: String) {
        _uiState.value = _uiState.value.copy(
            deadline = value,
            bisaSimpan = hitungBisaSimpan(judul = _uiState.value.judul, mataKuliah = _uiState.value.mataKuliah, deadline = value)
        )
    }

    // Menghitung apakah tombol Simpan boleh diklik (semua field harus diisi)
    private fun hitungBisaSimpan(judul: String, mataKuliah: String, deadline: String): Boolean =
        judul.isNotBlank() && mataKuliah.isNotBlank() && deadline.isNotBlank()

    // Dipanggil saat tombol Simpan Tugas diklik. Membuat objek Task dan menyimpannya.
    fun simpan() {
        viewModelScope.launch {
            val task = Task(
                id = java.util.UUID.randomUUID().toString(),
                title = _uiState.value.judul,
                course = Course(
                    id = "MK-01",
                    name = _uiState.value.mataKuliah,
                    code = ""
                ),
                deadline = _uiState.value.deadline,
                status = TaskStatus.Pending
            )
            // Simpan tugas melalui repository (saat ini simulasi, belum ada method simpan di repository)
            // repository.simpan(task)
        }
    }
}
