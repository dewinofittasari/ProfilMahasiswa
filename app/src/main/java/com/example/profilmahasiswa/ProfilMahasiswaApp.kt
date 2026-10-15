package com.example.profilmahasiswa

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.profilmahasiswa.ui.theme.AccentBlue
import com.example.profilmahasiswa.ui.theme.BorderColor
import com.example.profilmahasiswa.ui.theme.CardBackground
import com.example.profilmahasiswa.ui.theme.DisabledGray
import com.example.profilmahasiswa.ui.theme.Divider
import com.example.profilmahasiswa.ui.theme.ErrorRed
import com.example.profilmahasiswa.ui.theme.LightBlue
import com.example.profilmahasiswa.ui.theme.PrimaryBlue
import com.example.profilmahasiswa.ui.theme.PrimaryBlueDark
import com.example.profilmahasiswa.ui.theme.SurfaceBlue
import com.example.profilmahasiswa.ui.theme.TextHint
import com.example.profilmahasiswa.ui.theme.TextPrimary
import com.example.profilmahasiswa.ui.theme.TextSecondary
import com.example.profilmahasiswa.ui.theme.UniverMuliaBlue
import com.example.profilmahasiswa.ui.theme.UniverMuliaDeepBlue
import com.example.profilmahasiswa.ui.theme.UniverMuliaDarkBlue
import com.example.profilmahasiswa.ui.theme.UniverMuliaGold
import com.example.profilmahasiswa.ui.theme.UniverMuliaGreen
import com.example.profilmahasiswa.ui.theme.UniverMuliaRed

// ─────────────────────────────────────────────────────────────────────────────
// Root composable
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun ProfilMahasiswaApp(viewModel: ProfilViewModel = viewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }

    // Trigger Snackbar when ViewModel signals it
    LaunchedEffect(viewModel.showSnackbar) {
        if (viewModel.showSnackbar) {
            val result = snackbarHostState.showSnackbar(
                message = "✅  Data berhasil disimpan!",
                actionLabel = "OK",
                duration = SnackbarDuration.Short
            )
            if (result == SnackbarResult.ActionPerformed || result == SnackbarResult.Dismissed) {
                viewModel.onSnackbarDismissed()
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
            )
        },
        containerColor = Color(0xFFF5F7FF)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Top university header bar
            UniversityHeader()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                // Page title
                Text(
                    text = "Form Profil Mahasiswa",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    modifier = Modifier.padding(vertical = 12.dp)
                )

                // Mini profile preview card (top)
                MiniProfileCard(profile = viewModel.savedProfile)

                Spacer(modifier = Modifier.height(16.dp))

                // Input fields
                FormSection(viewModel = viewModel)

                Spacer(modifier = Modifier.height(20.dp))

                // Save button
                SaveButton(
                    enabled = viewModel.isFormValid,
                    onClick = viewModel::simpanData
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Digital card section
                DigitalKartuSection(profile = viewModel.savedProfile)

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// University header
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun UniversityHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(UniverMuliaDarkBlue, UniverMuliaBlue)
                )
            )
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Logo placeholder (colored circle)
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.15f))
                    .border(2.dp, UniverMuliaGold, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "UM",
                    color = UniverMuliaGold,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp
                )
            }

            Column {
                Text(
                    text = "SISTEM AKADEMIK TERPADU",
                    color = UniverMuliaGold,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.5.sp
                )
                Text(
                    text = "UNIVERSITAS MULIA",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircleDot(color = UniverMuliaGold)
                    Text(
                        text = "Inovatif  •  Mandiri  •  Humanis",
                        color = Color.White.copy(alpha = 0.80f),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun CircleDot(color: Color) {
    Box(
        modifier = Modifier
            .size(6.dp)
            .clip(CircleShape)
            .background(color)
    )
}

// ─────────────────────────────────────────────────────────────────────────────
// Mini profile preview card (shown at top of form)
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun MiniProfileCard(profile: MahasiswaProfile?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (profile != null) PrimaryBlue else Color(0xFFE8ECF8)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (profile != null) {
                    Text(
                        text = profile.nama.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        text = "?",
                        color = TextHint,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column {
                Text(
                    text = profile?.nama ?: "Belum ada data",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = if (profile != null) TextPrimary else TextSecondary
                )
                Text(
                    text = "NIM: ${profile?.nim ?: "-"}",
                    fontSize = 13.sp,
                    color = TextSecondary
                )
                if (profile != null) {
                    Text(
                        text = profile.programStudi,
                        fontSize = 12.sp,
                        color = PrimaryBlue,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Input form section
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun FormSection(viewModel: ProfilViewModel) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Nama Lengkap
        ProfileTextField(
            label = "Nama Lengkap",
            value = viewModel.namaInput,
            onValueChange = viewModel::onNamaChange,
            placeholder = "Masukkan nama lengkap",
            leadingIcon = Icons.Default.Person,
            errorMessage = viewModel.namaError,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            onFocusLost = viewModel::onNamaFocusLost
        )

        // NIM
        ProfileTextField(
            label = "NIM (Nomor Induk Mahasiswa)",
            value = viewModel.nimInput,
            onValueChange = viewModel::onNimChange,
            placeholder = "Contoh: 2411009",
            leadingIcon = Icons.Default.Badge,
            errorMessage = viewModel.nimError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            onFocusLost = viewModel::onNimFocusLost
        )

        // Program Studi
        ProfileTextField(
            label = "Program Studi",
            value = viewModel.prodiInput,
            onValueChange = viewModel::onProdiChange,
            placeholder = "Contoh: S1 Informatika",
            leadingIcon = Icons.Default.Star,
            errorMessage = viewModel.prodiError,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Done
            ),
            onFocusLost = viewModel::onProdiFocusLost
        )
    }
}

@Composable
private fun ProfileTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: ImageVector,
    errorMessage: String?,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onFocusLost: () -> Unit = {}
) {
    val isError = errorMessage != null
    val borderColor = when {
        isError -> ErrorRed
        value.isNotEmpty() -> PrimaryBlue
        else -> BorderColor
    }

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        // Label row
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Text(
                text = " *",
                color = ErrorRed,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused) onFocusLost()
                },
            placeholder = {
                Text(
                    text = placeholder,
                    color = TextHint,
                    fontSize = 14.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = if (isError) ErrorRed else if (value.isNotEmpty()) PrimaryBlue else TextHint,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = value.isNotEmpty() && !isError,
                    enter = fadeIn(tween(200)),
                    exit = fadeOut(tween(200))
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Valid",
                        tint = UniverMuliaGreen,
                        modifier = Modifier.size(18.dp)
                    )
                }
            },
            isError = isError,
            supportingText = if (isError) {
                { Text(text = errorMessage!!, color = ErrorRed, fontSize = 12.sp) }
            } else null,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryBlue,
                unfocusedBorderColor = BorderColor,
                errorBorderColor = ErrorRed,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                errorContainerColor = Color(0xFFFFF5F5),
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                cursorColor = PrimaryBlue
            )
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Save button
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun SaveButton(enabled: Boolean, onClick: () -> Unit) {
    val scale by animateFloatAsState(
        targetValue = if (enabled) 1f else 0.97f,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
        label = "buttonScale"
    )

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .scale(scale),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryBlue,
            disabledContainerColor = DisabledGray,
            contentColor = Color.White,
            disabledContentColor = Color.White.copy(alpha = 0.7f)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (enabled) 4.dp else 0.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = "Simpan Perubahan",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.3.sp
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Digital student ID card
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun DigitalKartuSection(profile: MahasiswaProfile?) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Section label
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(20.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(PrimaryBlue)
            )
            Text(
                text = "Kartu Digital Mahasiswa",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }

        DigitalStudentCard(profile = profile)
    }
}

@Composable
private fun DigitalStudentCard(profile: MahasiswaProfile?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(UniverMuliaDarkBlue, UniverMuliaDeepBlue)
                    )
                )
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // ── Card top accent stripe ─────────────────────────────────
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(5.dp)
                            .background(UniverMuliaRed)
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(5.dp)
                            .background(UniverMuliaGold)
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(5.dp)
                            .background(Color(0xFF1565C0))
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(5.dp)
                            .background(UniverMuliaGreen)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Header label
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "KARTU DIGITAL",
                            color = UniverMuliaGold,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp
                        )
                        Text(
                            text = "•",
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 10.sp
                        )
                        Text(
                            text = "SEMESTER GENAP",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 1.sp
                        )
                    }

                    // Card title row with avatar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Profil Mahasiswa",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        // Avatar circle
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.12f))
                                .border(
                                    width = 2.dp,
                                    color = UniverMuliaGold.copy(alpha = 0.6f),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (profile != null) {
                                Text(
                                    text = profile.nama.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = null,
                                    tint = Color.White.copy(alpha = 0.5f),
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    }

                    HorizontalDivider(
                        color = Color.White.copy(alpha = 0.15f),
                        thickness = 1.dp
                    )

                    // Data rows
                    CardDataRow(label = "Nama", value = profile?.nama)
                    CardDataRow(label = "NIM", value = profile?.nim)
                    CardDataRow(label = "Program Studi", value = profile?.programStudi)

                    HorizontalDivider(
                        color = Color.White.copy(alpha = 0.15f),
                        thickness = 1.dp
                    )

                    // Footer
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Perguruan Tinggi",
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 13.sp
                        )
                        Text(
                            text = "Universitas Mulia",
                            color = UniverMuliaGold,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Bottom bar with school icon
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.2f),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Sistem Akademik Terpadu – UM",
                            color = Color.White.copy(alpha = 0.25f),
                            fontSize = 10.sp,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CardDataRow(label: String, value: String?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.60f),
            fontSize = 13.sp,
            modifier = Modifier.weight(0.4f)
        )
        Text(
            text = value ?: "–",
            color = if (value != null) Color.White else Color.White.copy(alpha = 0.35f),
            fontSize = 13.sp,
            fontWeight = if (value != null) FontWeight.Medium else FontWeight.Normal,
            modifier = Modifier.weight(0.6f),
            textAlign = TextAlign.End,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}
