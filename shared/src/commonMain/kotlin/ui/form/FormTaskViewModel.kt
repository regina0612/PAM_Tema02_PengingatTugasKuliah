
package ui.form

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FormTaskViewModel {

    private val _uiState = MutableStateFlow(TaskUiState())

    val uiState: StateFlow<TaskUiState> =
        _uiState.asStateFlow()

    fun onTitleChange(title: String) {
        _uiState.update { state ->
            state.copy(
                title = title,
                errorMessage = null
            )
        }
    }

    fun onDescriptionChange(description: String) {
        _uiState.update { state ->
            state.copy(
                description = description
            )
        }
    }

    fun resetForm() {
        _uiState.value = TaskUiState()
    }

    fun validateForm(): Boolean {
        val title = _uiState.value.title.trim()

        return if (title.isEmpty()) {
            _uiState.update { state ->
                state.copy(
                    errorMessage = "Judul tugas tidak boleh kosong"
                )
            }
            false
        } else {
            _uiState.update { state ->
                state.copy(
                    errorMessage = null
                )
            }
            true
        }
    }
}
