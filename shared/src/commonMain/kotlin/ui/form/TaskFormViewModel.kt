
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
import java.util.UUID

// Menyimpan kondisi input pada form tugas.
data class TaskFormUiState(
    val judul: String = "",
    val mataKuliah: String = "",
    val deadline: String = "",
    val bisaSimpan: Boolean = false
)

// Mengelola input dan proses penyimpanan tugas.
class TaskFormViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskFormUiState())
    val uiState: StateFlow<TaskFormUiState> = _uiState.asStateFlow()

    // Memperbarui judul tugas.
    fun updateJudul(value: String) {
        _uiState.value = _uiState.value.copy(
            judul = value,
            bisaSimpan = hitungBisaSimpan(
                value,
                _uiState.value.mataKuliah,
                _uiState.value.deadline
            )
        )
    }

    // Memperbarui nama mata kuliah.
    fun updateMataKuliah(value: String) {
        _uiState.value = _uiState.value.copy(
            mataKuliah = value,
            bisaSimpan = hitungBisaSimpan(
                _uiState.value.judul,
                value,
                _uiState.value.deadline
            )
        )
    }

    // Memperbarui deadline tugas.
    fun updateDeadline(value: String) {
        _uiState.value = _uiState.value.copy(
            deadline = value,
            bisaSimpan = hitungBisaSimpan(
                _uiState.value.judul,
                _uiState.value.mataKuliah,
                value
            )
        )
    }

    // Tombol simpan aktif jika semua kolom sudah diisi.
    private fun hitungBisaSimpan(
        judul: String,
        mataKuliah: String,
        deadline: String
    ): Boolean {
        return judul.isNotBlank() &&
                mataKuliah.isNotBlank() &&
                deadline.isNotBlank()
    }

    // Membuat tugas baru dan menyimpannya ke repository.
    fun simpan() {
        val state = _uiState.value

        if (!state.bisaSimpan) return

        val task = Task(
            id = UUID.randomUUID().toString(),
            title = state.judul.trim(),
            course = Course(
                id = "MK-01",
                name = state.mataKuliah.trim(),
                code = ""
            ),
            deadline = state.deadline.trim(),
            status = TaskStatus.Pending,
            description = null
        )

        viewModelScope.launch {
            repository.addTask(task)

            // Mengosongkan form setelah tugas ditambahkan.
            _uiState.value = TaskFormUiState()
        }
    }
}
