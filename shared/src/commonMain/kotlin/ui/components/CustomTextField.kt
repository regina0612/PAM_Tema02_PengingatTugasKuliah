package ui.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// Komponen input teks yang dapat digunakan kembali, menggunakan OutlinedTextField Material3
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    // OutlinedTextField adalah kotak input teks dengan garis tepi (outline) dari Material3.
    // - value      : teks yang saat ini ditampilkan di field
    // - onValueChange : dipanggil setiap kali teks berubah (misalnya saat user mengetik)
    // - label      : teks label yang tampil di atas/bolong di field input
    // - modifier   : modifier kustom yang dapat diterapkan dari luar komponen
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { androidx.compose.material3.Text(text = label) },
        modifier = modifier
    )
}
