
package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.TaskRepository
import tampilan.TaskListScreen

data class Anggota(
    val nama: String,
    val nim: String
)

@Composable
fun App() {
    val hijau = Color(0xFF567568)
    val krem = Color(0xFFF6F3EA)

    // Satu repository digunakan bersama oleh daftar dan form tugas.
    val repository = remember { TaskRepository() }
    val tasks by repository.tasks.collectAsState()

    var bukaDaftarTugas by remember { mutableStateOf(false) }

    val anggota = listOf(
        Anggota("Rakha Daffa Tama Truski", "124140196"),
        Anggota("Tarisa Menova", "124140039"),
        Anggota("Regina Cahyani Putri", "124140063"),
        Anggota("Raymond Kolose Nathanael Simbolon", "124140186")
    )

    MaterialTheme {
        if (bukaDaftarTugas) {
            TaskListScreen(
                tasks = tasks,
                repository = repository,
                onKembali = {
                    bukaDaftarTugas = false
                }
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(krem)
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "TemanTugas",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = hijau
                )

                Text(
                    text = "Pengingat Tugas Kuliah",
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Tentang Aplikasi",
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold,
                            color = hijau
                        )

                        Text(
                            text = "TemanTugas merupakan aplikasi yang " +
                                    "dibuat untuk membantu mahasiswa mencatat " +
                                    "tugas kuliah dan mengingat deadline " +
                                    "agar tugas dapat dikerjakan dengan teratur.",
                            fontSize = 14.sp
                        )

                        HorizontalDivider()

                        Text(
                            text = "Tema: Pengingat Tugas Kuliah",
                            fontSize = 15.sp
                        )

                        Text(
                            text = "Platform: ${getPlatform().name}",
                            fontSize = 15.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        bukaDaftarTugas = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = hijau
                    )
                ) {
                    Text(
                        text = "Lihat Daftar Tugas",
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Anggota Kelompok",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = hijau
                )

                Spacer(modifier = Modifier.height(10.dp))

                anggota.forEachIndexed { index, orang ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp)
                        ) {
                            Text(
                                text = "${index + 1}. ${orang.nama}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium
                            )

                            Text(
                                text = "NIM: ${orang.nim}",
                                fontSize = 14.sp,
                                color = hijau
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "PAM",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(
                        Alignment.CenterHorizontally
                    )
                )
            }
        }
    }
}
