package com.example.profilmahasiswa

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Root Composable untuk aplikasi Profil Mahasiswa.
 * Mendemonstrasikan State Hoisting, Unidirectional Data Flow, dan Recomposition.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilMahasiswaApp(viewModel: ProfilViewModel = viewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Praktikum Jetpack Compose",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        )
                        Text(
                            text = "State • Event • Recomposition",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B),
                            fontWeight = FontWeight.Medium
                        )
                    }
                },
                actions = {
                    // Tombol Isi Cepat Data Dewi Nofitta Sari
                    IconButton(
                        onClick = { viewModel.isiDataDewi() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Isi Data Dewi",
                            tint = Color(0xFF2563EB)
                        )
                    }
                    // Tombol Reset
                    IconButton(
                        onClick = { viewModel.resetForm() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reset Form",
                            tint = Color(0xFF64748B)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                modifier = Modifier.shadow(2.dp)
            )
        },
        containerColor = Color(0xFFF1F5F9)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ─────────────────────────────────────────────────────────────────
            // 1. KARTU PRATINJAU LANGSUNG (HEADER GRADASI BERANIMASI)
            // ─────────────────────────────────────────────────────────────────
            LivePreviewCard(
                nama = viewModel.namaInput,
                nim = viewModel.nimInput,
                email = viewModel.emailInput,
                prodi = viewModel.prodiInput,
                semester = viewModel.semesterInput.toInt(),
                isAktif = viewModel.isAktifInput,
                inisial = viewModel.inisialNama
            )

            // ─────────────────────────────────────────────────────────────────
            // 2. BILAH KELENGKAPAN PROFIL (0 - 100%) BERANIMASI
            // ─────────────────────────────────────────────────────────────────
            CompletionProgressBar(
                progress = viewModel.completionPercentage,
                isNamaValid = viewModel.isNamaValid,
                isNimValid = viewModel.isNimValid,
                isEmailValid = viewModel.isEmailValid,
                isProdiValid = viewModel.isProdiValid
            )

            // ─────────────────────────────────────────────────────────────────
            // 3. BANNER SUKSES (ANIMATED VISIBILITY)
            // ─────────────────────────────────────────────────────────────────
            AnimatedVisibility(
                visible = viewModel.showSuccessBanner,
                enter = fadeIn(tween(300)) + expandVertically(spring(stiffness = Spring.StiffnessMediumLow)),
                exit = fadeOut(tween(200)) + shrinkVertically()
            ) {
                SuccessBanner(
                    profile = viewModel.savedProfile,
                    onDismiss = { viewModel.tutupBanner() }
                )
            }

            // ─────────────────────────────────────────────────────────────────
            // 4. FORMULIR INPUT MAHASISWA
            // ─────────────────────────────────────────────────────────────────
            FormInputCard(
                viewModel = viewModel
            )

            // ─────────────────────────────────────────────────────────────────
            // 5. PENJELASAN KONSEP STATE, EVENT & RECOMPOSITION
            // ─────────────────────────────────────────────────────────────────
            ComposeConceptCard()

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// KOMPONEN 1: KARTU PRATINJAU LANGSUNG DENGAN GRADASI BERANIMASI
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun LivePreviewCard(
    nama: String,
    nim: String,
    email: String,
    prodi: String,
    semester: Int,
    isAktif: Boolean,
    inisial: String,
    modifier: Modifier = Modifier
) {
    // Animasi transisi warna gradasi berdasarkan status aktif (Aktif vs Cuti)
    val startColor by animateColorAsState(
        targetValue = if (isAktif) Color(0xFF1E3A8A) else Color(0xFF334155),
        animationSpec = tween(600),
        label = "GradStartColor"
    )
    val midColor by animateColorAsState(
        targetValue = if (isAktif) Color(0xFF4338CA) else Color(0xFF475569),
        animationSpec = tween(600),
        label = "GradMidColor"
    )
    val endColor by animateColorAsState(
        targetValue = if (isAktif) Color(0xFF0D9488) else Color(0xFF64748B),
        animationSpec = tween(600),
        label = "GradEndColor"
    )

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(startColor, midColor, endColor)
                    )
                )
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Baris Atas: Tag Live Recomposition & Badge Status Mahasiswa
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Tag Recomposition
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White.copy(alpha = 0.18f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "⚡",
                                fontSize = 11.sp
                            )
                            Text(
                                text = "LIVE PREVIEW",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.8.sp
                            )
                        }
                    }

                    // Badge Status Aktif/Non-Aktif
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isAktif) Color(0xFF10B981).copy(alpha = 0.25f)
                        else Color(0xFFEF4444).copy(alpha = 0.25f),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isAktif) Color(0xFF6EE7B7) else Color(0xFFFCA5A5)
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(
                                        color = if (isAktif) Color(0xFF34D399) else Color(0xFFF87171),
                                        shape = CircleShape
                                    )
                            )
                            Text(
                                text = if (isAktif) "MAHASISWA AKTIF" else "NON-AKTIF / CUTI",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                // Baris Tengah: Avatar Inisial & Identitas Utama (Nama, NIM)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Avatar Inisial Bulat
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(68.dp)
                            .shadow(4.dp, CircleShape)
                            .background(Color.White.copy(alpha = 0.22f), CircleShape)
                            .border(2.dp, Color.White.copy(alpha = 0.6f), CircleShape)
                    ) {
                        Text(
                            text = inisial,
                            color = Color.White,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                    }

                    // Kolom Nama & NIM
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = if (nama.isNotBlank()) nama else "Nama Mahasiswa",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            fontStyle = if (nama.isBlank()) FontStyle.Italic else FontStyle.Normal
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Badge,
                                contentDescription = "NIM",
                                tint = Color.White.copy(alpha = 0.85f),
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = if (nim.isNotBlank()) "NIM: $nim" else "NIM: Belum diisi",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 14.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                HorizontalDivider(
                    color = Color.White.copy(alpha = 0.2f),
                    thickness = 1.dp
                )

                // Baris Bawah: Program Studi, Semester, dan Email
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Badge Program Studi
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color.White.copy(alpha = 0.16f),
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.School,
                                    contentDescription = "Prodi",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = if (prodi.isNotBlank()) prodi else "Pilih Prodi",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        // Badge Semester
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color.White.copy(alpha = 0.16f)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Semester",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Semester $semester",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    // Baris Email
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.padding(start = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email",
                            tint = Color.White.copy(alpha = 0.85f),
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = if (email.isNotBlank()) email else "email@kampus.ac.id",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontStyle = if (email.isBlank()) FontStyle.Italic else FontStyle.Normal
                        )
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// KOMPONEN 2: BILAH KELENGKAPAN PROFIL (0 - 100%) BERANIMASI
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun CompletionProgressBar(
    progress: Float,
    isNamaValid: Boolean,
    isNimValid: Boolean,
    isEmailValid: Boolean,
    isProdiValid: Boolean,
    modifier: Modifier = Modifier
) {
    // Animasi nilai progres dari 0f sampai 1f
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "ProgressAnimation"
    )

    // Animasi warna progress bar: Amber jika belum 100%, Hijau toska jika 100%
    val progressColor by animateColorAsState(
        targetValue = if (progress >= 1f) Color(0xFF10B981) else Color(0xFF3B82F6),
        animationSpec = tween(400),
        label = "ProgressColor"
    )

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = if (progress >= 1f) Icons.Default.CheckCircle else Icons.Default.Info,
                        contentDescription = "Status",
                        tint = progressColor,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Kelengkapan Profil",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color(0xFF1E293B)
                    )
                }
                Text(
                    text = "${(animatedProgress * 100).toInt()}%",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    color = progressColor
                )
            }

            // Bilah Progres Linear Beranimasi
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp)),
                color = progressColor,
                trackColor = Color(0xFFE2E8F0),
                strokeCap = StrokeCap.Round
            )

            // Indikator Checklist Singkat
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MilestoneItem(label = "Nama", isDone = isNamaValid)
                MilestoneItem(label = "NIM", isDone = isNimValid)
                MilestoneItem(label = "Email", isDone = isEmailValid)
                MilestoneItem(label = "Prodi", isDone = isProdiValid)
                MilestoneItem(label = "Semester", isDone = true)
            }
        }
    }
}

@Composable
private fun MilestoneItem(label: String, isDone: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Icon(
            imageVector = if (isDone) Icons.Default.CheckCircle else Icons.Default.Close,
            contentDescription = null,
            tint = if (isDone) Color(0xFF10B981) else Color(0xFF94A3B8),
            modifier = Modifier.size(13.dp)
        )
        Text(
            text = label,
            fontSize = 11.sp,
            color = if (isDone) Color(0xFF334155) else Color(0xFF94A3B8),
            fontWeight = if (isDone) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// KOMPONEN 3: BANNER SUKSES (ANIMATED VISIBILITY)
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun SuccessBanner(
    profile: MahasiswaProfile?,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (profile == null) return

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFECFDF5)),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF6EE7B7)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color(0xFF10B981), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Sukses",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "Profil Berhasil Disimpan!",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color(0xFF065F46)
                        )
                        Text(
                            text = "State profil tersimpan & recomposition aktif",
                            fontSize = 12.sp,
                            color = Color(0xFF047857)
                        )
                    }
                }
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Tutup",
                        tint = Color(0xFF065F46),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            HorizontalDivider(color = Color(0xFFA7F3D0), thickness = 1.dp)

            // Ringkasan Data Tersimpan
            Column(
                modifier = Modifier.padding(top = 4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "• Mahasiswa: ${profile.nama} (${profile.nim})",
                    fontSize = 13.sp,
                    color = Color(0xFF064E3B),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "• Program Studi: ${profile.programStudi} - Semester ${profile.semester}",
                    fontSize = 13.sp,
                    color = Color(0xFF064E3B)
                )
                Text(
                    text = "• Email: ${profile.email}",
                    fontSize = 13.sp,
                    color = Color(0xFF064E3B)
                )
                Text(
                    text = "• Status: ${if (profile.isAktif) "Aktif Mengikuti Perkuliahan" else "Non-Aktif / Cuti"}",
                    fontSize = 13.sp,
                    color = Color(0xFF064E3B),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// KOMPONEN 4: FORMULIR INPUT MAHASISWA
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FormInputCard(
    viewModel: ProfilViewModel,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Header Form
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = "Formulir Profil Mahasiswa",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
                Text(
                    text = "Ketik atau ubah nilai di bawah untuk melihat live recomposition",
                    fontSize = 12.sp,
                    color = Color(0xFF64748B)
                )
            }

            HorizontalDivider(color = Color(0xFFF1F5F9))

            // 1. INPUT NAMA
            OutlinedTextField(
                value = viewModel.namaInput,
                onValueChange = viewModel::onNamaChange,
                label = { Text("Nama Lengkap") },
                placeholder = { Text("Contoh: Dewi Nofitta Sari") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Nama",
                        tint = Color(0xFF2563EB)
                    )
                },
                trailingIcon = {
                    if (viewModel.namaInput.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onNamaChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Hapus Nama",
                                tint = Color(0xFF94A3B8)
                            )
                        }
                    }
                },
                isError = viewModel.namaError != null,
                supportingText = {
                    viewModel.namaError?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2563EB),
                    unfocusedBorderColor = Color(0xFFCBD5E1)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // 2. INPUT NIM (HANYA ANGKA)
            OutlinedTextField(
                value = viewModel.nimInput,
                onValueChange = viewModel::onNimChange,
                label = { Text("Nomor Induk Mahasiswa (NIM)") },
                placeholder = { Text("Contoh: 2411065") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Badge,
                        contentDescription = "NIM",
                        tint = Color(0xFF2563EB)
                    )
                },
                trailingIcon = {
                    if (viewModel.nimInput.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onNimChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Hapus NIM",
                                tint = Color(0xFF94A3B8)
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = viewModel.nimError != null,
                supportingText = {
                    if (viewModel.nimError != null) {
                        Text(text = viewModel.nimError!!, color = MaterialTheme.colorScheme.error)
                    } else {
                        Text("Hanya angka (contoh: 2411065)", color = Color(0xFF64748B))
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2563EB),
                    unfocusedBorderColor = Color(0xFFCBD5E1)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // 3. INPUT EMAIL (VALIDASI FORMAT)
            OutlinedTextField(
                value = viewModel.emailInput,
                onValueChange = viewModel::onEmailChange,
                label = { Text("Alamat Email Kampus") },
                placeholder = { Text("Contoh: dewi.nofitta@mhs.ac.id") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email",
                        tint = Color(0xFF2563EB)
                    )
                },
                trailingIcon = {
                    if (viewModel.emailInput.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onEmailChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Hapus Email",
                                tint = Color(0xFF94A3B8)
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = viewModel.emailError != null,
                supportingText = {
                    if (viewModel.emailError != null) {
                        Text(text = viewModel.emailError!!, color = MaterialTheme.colorScheme.error)
                    } else {
                        Text("Format email valid (mengandung @ dan domain)", color = Color(0xFF64748B))
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2563EB),
                    unfocusedBorderColor = Color(0xFFCBD5E1)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // 4. PILIH PROGRAM STUDI (CHIP)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = null,
                        tint = Color(0xFF2563EB),
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "Program Studi",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color(0xFF1E293B)
                    )
                }

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    viewModel.daftarProdi.forEach { prodi ->
                        val isSelected = viewModel.prodiInput == prodi
                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.onProdiSelect(prodi) },
                            label = {
                                Text(
                                    text = prodi,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            },
                            leadingIcon = if (isSelected) {
                                {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Terpilih",
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            } else null,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF2563EB),
                                selectedLabelColor = Color.White,
                                selectedLeadingIconColor = Color.White,
                                containerColor = Color(0xFFF8FAFC),
                                labelColor = Color(0xFF334155)
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = Color(0xFFCBD5E1),
                                selectedBorderColor = Color(0xFF2563EB)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }
            }

            // 5. PILIH SEMESTER (SLIDER)
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            tint = Color(0xFF2563EB),
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Semester",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF1E293B)
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFFEFF6FF)
                    ) {
                        Text(
                            text = "Semester ${viewModel.semesterInput.toInt()}",
                            color = Color(0xFF1D4ED8),
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                Slider(
                    value = viewModel.semesterInput,
                    onValueChange = viewModel::onSemesterChange,
                    valueRange = 1f..8f,
                    steps = 6,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFF2563EB),
                        activeTrackColor = Color(0xFF2563EB),
                        inactiveTrackColor = Color(0xFFE2E8F0)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Sem 1", fontSize = 11.sp, color = Color(0xFF94A3B8))
                    Text(text = "Sem 4", fontSize = 11.sp, color = Color(0xFF94A3B8))
                    Text(text = "Sem 8", fontSize = 11.sp, color = Color(0xFF94A3B8))
                }
            }

            // 6. STATUS MAHASISWA AKTIF (SWITCH)
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFF8FAFC),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "Status Mahasiswa Aktif",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF1E293B)
                        )
                        Text(
                            text = if (viewModel.isAktifInput)
                                "Warna gradasi kartu di atas menjadi biru-teal dinamis"
                            else
                                "Warna gradasi kartu di atas bertransisi menjadi abu-abu redup",
                            fontSize = 11.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                    Switch(
                        checked = viewModel.isAktifInput,
                        onCheckedChange = viewModel::onAktifChange,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color(0xFF10B981),
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = Color(0xFF94A3B8)
                        )
                    )
                }
            }

            // 7. TOMBOL SIMPAN
            Button(
                onClick = viewModel::simpanProfil,
                enabled = viewModel.isFormComplete,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2563EB),
                    disabledContainerColor = Color(0xFFCBD5E1),
                    contentColor = Color.White,
                    disabledContentColor = Color(0xFF64748B)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "Simpan",
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = if (viewModel.isFormComplete) "Simpan Data Profil" else "Lengkapi Form untuk Simpan",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }

            if (!viewModel.isFormComplete) {
                Text(
                    text = "⚠️ Tombol Simpan aktif setelah Nama, NIM, Email, dan Prodi terisi valid (100%).",
                    fontSize = 11.sp,
                    color = Color(0xFF64748B),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // TOMBOL CEPAT ISI DATA SAYA (DEWI NOFITTA SARI - 2411065)
            OutlinedButton(
                onClick = { viewModel.isiDataDewi() },
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF93C5FD)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Color(0xFF2563EB),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Isi Data Otomatis: Dewi Nofitta Sari (2411065)",
                        color = Color(0xFF1D4ED8),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// KOMPONEN 5: KARTU EDUKASI STATE, EVENT & RECOMPOSITION
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun ComposeConceptCard(modifier: Modifier = Modifier) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                        contentDescription = null,
                        tint = Color(0xFF2563EB),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Konsep Jetpack Compose di Aplikasi Ini",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF1E293B)
                    )
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Tutup" else "Buka",
                    tint = Color(0xFF64748B)
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ConceptItem(
                        badge = "STATE",
                        badgeColor = Color(0xFF3B82F6),
                        title = "Nilai yang Diobservasi",
                        description = "Disimpan di ViewModel menggunakan 'mutableStateOf' (nama, nim, email, prodi, semester, isAktif). Menjadi satu-satunya sumber kebenaran (Single Source of Truth)."
                    )
                    ConceptItem(
                        badge = "EVENT",
                        badgeColor = Color(0xFF10B981),
                        title = "Aksi Pengguna",
                        description = "Setiap interaksi pengguna (ketik nama, pilih chip prodi, geser slider semester, toggle switch) mengirimkan event ke ViewModel untuk memperbarui state."
                    )
                    ConceptItem(
                        badge = "RECOMPOSITION",
                        badgeColor = Color(0xFF8B5CF6),
                        title = "Pembaruan UI Cerdas",
                        description = "Saat state berubah, Compose hanya me-render ulang fungsi Composable yang membaca state tersebut (Kartu Pratinjau, Progress Bar, Tombol Simpan), tanpa reload layar."
                    )
                }
            }
        }
    }
}

@Composable
private fun ConceptItem(
    badge: String,
    badgeColor: Color,
    title: String,
    description: String
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF8FAFC),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = badgeColor
                ) {
                    Text(
                        text = badge,
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = Color(0xFF0F172A)
                )
            }
            Text(
                text = description,
                fontSize = 12.sp,
                color = Color(0xFF475569),
                lineHeight = 16.sp
            )
        }
    }
}
