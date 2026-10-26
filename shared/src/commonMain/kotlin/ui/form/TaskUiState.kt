package ui.form

data class TaskUiState(
    val title: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
