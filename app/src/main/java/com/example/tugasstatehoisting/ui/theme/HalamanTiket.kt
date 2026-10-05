package com.example.tugastatehoisting.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun HalamanTiketParent() {
    var nama by rememberSaveable { mutableStateOf("") }
    var jumlah by rememberSaveable { mutableIntStateOf(1) }
    val hargaTiket = 50000

    var pesanStatus by remember { mutableStateOf("Status: Silakan pesan tiket") }
    var isMemproses by remember { mutableStateOf(false) }

    LaunchedEffect(isMemproses) {
        if (isMemproses) {
            pesanStatus = "Status: Memproses pesanan..."
            delay(5000) // Delay 5 detik[cite: 5, 6]
            pesanStatus = "Status: Tiket berhasil dipesan!"
            isMemproses = false
        }
    }

    HalamanTiketChild(
        nama = nama,
        jumlah = jumlah,
        pesanStatus = pesanStatus,
        isMemproses = isMemproses,
        onNamaChange = { nama = it },
        onTambah = { jumlah++ },
        onKurang = { if (jumlah > 1) jumlah-- },
        onPesanClick = {
            if (nama.isBlank()) {
                pesanStatus = "Status: Nama harus diisi"
            } else {
                isMemproses = true
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanTiketChild(
    nama: String,
    jumlah: Int,
    pesanStatus: String,
    isMemproses: Boolean,
    onNamaChange: (String) -> Unit,
    onTambah: () -> Unit,
    onKurang: () -> Unit,
    onPesanClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pemesanan Tiket", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF800000))
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text("Nama", fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = nama,
                onValueChange = onNamaChange,
                placeholder = { Text("Masukkan nama Anda") },
                enabled = !isMemproses,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Jumlah Tiket", fontWeight = FontWeight.Bold)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = onKurang,
                    enabled = !isMemproses,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF800000))
                ) {
                    Text("-", fontSize = 20.sp)
                }

                Text("$jumlah", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                Button(
                    onClick = onTambah,
                    enabled = !isMemproses,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF800000))
                ) {
                    Text("+", fontSize = 20.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onPesanClick,
                enabled = !isMemproses,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF800000))
            ) {
                Text("Pesan Tiket", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Tentukan warna latar dan teks berdasarkan isi pesanStatus
            val (bgColor, textColor) = when {
                pesanStatus.contains("Memproses") -> Pair(Color(0xFFE3F2FD), Color(0xFF1976D2)) // Biru
                pesanStatus.contains("berhasil") -> Pair(Color(0xFFE8F5E9), Color(0xFF2E7D32)) // Hijau
                pesanStatus.contains("harus diisi") -> Pair(Color(0xFFFFEBEE), Color(0xFFC62828)) // Merah
                else -> Pair(Color(0xFFF5F5F5), Color.DarkGray) // Netral/Default
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(bgColor, shape = RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isMemproses) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = textColor,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        text = pesanStatus,
                        color = textColor,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}