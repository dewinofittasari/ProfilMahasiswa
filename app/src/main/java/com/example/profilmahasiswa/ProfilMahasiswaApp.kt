package com.example.profilmahasiswa

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
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
import com.example.profilmahasiswa.ui.theme.DisabledGray
import com.example.profilmahasiswa.ui.theme.ErrorRed
import com.example.profilmahasiswa.ui.theme.PrimaryBlue
import com.example.profilmahasiswa.ui.theme.TextHint
import com.example.profilmahasiswa.ui.theme.TextPrimary
import com.example.profilmahasiswa.ui.theme.TextSecondary
import com.example.profilmahasiswa.ui.theme.UniverMuliaBlue
import com.example.profilmahasiswa.ui.theme.UniverMuliaDarkBlue
import com.example.profilmahasiswa.ui.theme.UniverMuliaDeepBlue
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
                message = "✅  Data profil berhasil disimpan!",
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
        containerColor = Color(0xFFF0F4FF)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // ── University header with building visual ──
            UniversityHeader()

            // ── Form card ──
            FormCard(viewModel = viewModel)

            Spacer(modifier = Modifier.height(16.dp))

            // ── Digital student card ──
            DigitalStudentCard(profile = viewModel.savedProfile)

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// University header with building background effect
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun UniversityHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        UniverMuliaDarkBlue,
                        UniverMuliaBlue,
                        Color(0xFF2A5FAA)
                    )
                )
            )
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        // Right side – simulated building silhouette pattern
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp, top = 8.dp, bottom = 8.dp)
        ) {
            // Simulated building blocks
            Row(
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                // Building silhouettes using boxes
                BuildingBlock(width = 18, height = 50, alpha = 0.10f)
                BuildingBlock(width = 22, height = 65, alpha = 0.12f)
                BuildingBlock(width = 20, height = 45, alpha = 0.08f)
                BuildingBlock(width = 26, height = 72, alpha = 0.14f)
                BuildingBlock(width = 16, height = 38, alpha = 0.10f)
            }
            // Label overlay
            Text(
                text = "UNIVERSITAS MULIA",
                color = Color.White.copy(alpha = 0.20f),
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 4.dp, bottom = 2.dp)
            )
        }

        // Left side – Logo + Text
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // University logo placeholder
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.12f))
                    .border(2.dp, UniverMuliaGold, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = null,
                        tint = UniverMuliaGold,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = "UM",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
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
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DotIndicator(color = UniverMuliaGold)
                    Text(
                        text = "Inovatif",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                    DotIndicator(color = UniverMuliaGreen)
                    Text(
                        text = "Mandiri",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                    DotIndicator(color = AccentBlue)
                    Text(
                        text = "Humanis",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun BuildingBlock(width: Int, height: Int, alpha: Float) {
    Box(
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
            .clip(RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
            .background(Color.White.copy(alpha = alpha))
    )
}

@Composable
private fun DotIndicator(color: Color) {
    Box(
        modifier = Modifier
            .size(6.dp)
            .clip(CircleShape)
            .background(color)
    )
}

// ─────────────────────────────────────────────────────────────────────────────
// Main form card (white rounded card)
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun FormCard(viewModel: ProfilViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = (-12).dp)
            .padding(horizontal = 12.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            // ── Form title row ──
            FormTitleRow()

            Spacer(modifier = Modifier.height(16.dp))

            // ── Status card (Belum ada data / profile preview) ──
            StatusPreviewCard(profile = viewModel.savedProfile)

            Spacer(modifier = Modifier.height(20.dp))

            // ── Input: Nama Lengkap ──
            FormFieldWithIcon(
                icon = Icons.Default.PersonOutline,
                iconTint = PrimaryBlue,
                label = "Nama Lengkap",
                value = viewModel.namaInput,
                onValueChange = viewModel::onNamaChange,
                placeholder = "Masukkan nama lengkap",
                errorMessage = viewModel.namaError,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                onFocusLost = viewModel::onNamaFocusLost
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ── Input: NIM ──
            FormFieldWithIcon(
                icon = Icons.Default.Badge,
                iconTint = PrimaryBlue,
                label = "NIM (Nomor Induk Mahasiswa)",
                value = viewModel.nimInput,
                onValueChange = viewModel::onNimChange,
                placeholder = "Contoh: 2411009",
                errorMessage = viewModel.nimError,
                leadingIcon = Icons.Default.Badge,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                onFocusLost = viewModel::onNimFocusLost
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ── Input: Program Studi ──
            FormFieldWithIcon(
                icon = Icons.Default.School,
                iconTint = PrimaryBlue,
                label = "Program Studi",
                value = viewModel.prodiInput,
                onValueChange = viewModel::onProdiChange,
                placeholder = "Contoh: S1 Informatika",
                errorMessage = viewModel.prodiError,
                leadingIcon = Icons.Default.MenuBook,
                trailingIcon = Icons.Default.ArrowDropDown,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Done
                ),
                onFocusLost = viewModel::onProdiFocusLost
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── Save button ──
            SaveButton(
                enabled = viewModel.isFormValid,
                onClick = viewModel::simpanData
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Form title row
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun FormTitleRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            // Blue icon circle with person icon
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(PrimaryBlue),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            Column {
                Text(
                    text = "Form Profil Mahasiswa",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextPrimary
                )
                Text(
                    text = "Lengkapi data diri Anda untuk melanjutkan proses akademik.",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 16.sp
                )
            }
        }

        // Right side motto text
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(
                text = "Bersama",
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryBlue.copy(alpha = 0.7f),
                textAlign = TextAlign.End
            )
            Text(
                text = "Membangun",
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryBlue.copy(alpha = 0.7f),
                textAlign = TextAlign.End
            )
            Text(
                text = "Masa Depan",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryBlue,
                textAlign = TextAlign.End
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Status preview card (shows saved data or "Belum ada data")
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun StatusPreviewCard(profile: MahasiswaProfile?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (profile != null) Color(0xFFE8F5E9) else Color(0xFFF0F4FF)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Graduation cap icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (profile != null)
                            UniverMuliaGreen.copy(alpha = 0.15f)
                        else
                            PrimaryBlue.copy(alpha = 0.1f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    tint = if (profile != null) UniverMuliaGreen else PrimaryBlue,
                    modifier = Modifier.size(28.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (profile != null) profile.nama else "Belum ada data",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = TextPrimary
                )
                Text(
                    text = if (profile != null)
                        "NIM: ${profile.nim} • ${profile.programStudi}"
                    else
                        "Silakan lengkapi data profil mahasiswa Anda.",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 16.sp
                )
            }

            // Arrow button
            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(36.dp)
                    .border(
                        width = 1.dp,
                        color = if (profile != null)
                            UniverMuliaGreen.copy(alpha = 0.3f)
                        else
                            PrimaryBlue.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(10.dp)
                    ),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Detail",
                    tint = if (profile != null) UniverMuliaGreen else PrimaryBlue,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Form field with external icon label
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun FormFieldWithIcon(
    icon: ImageVector,
    iconTint: Color,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    errorMessage: String?,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onFocusLost: () -> Unit = {}
) {
    val isError = errorMessage != null
    val fieldBorderColor = when {
        isError -> ErrorRed
        value.isNotEmpty() -> PrimaryBlue
        else -> BorderColor
    }

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        // Label row with icon
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Text(
                text = "*",
                color = ErrorRed,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Text field
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
            leadingIcon = if (leadingIcon != null) {
                {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = if (isError) ErrorRed
                        else if (value.isNotEmpty()) PrimaryBlue
                        else TextHint,
                        modifier = Modifier.size(20.dp)
                    )
                }
            } else null,
            trailingIcon = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Valid checkmark
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
                    // Optional trailing icon (dropdown arrow)
                    if (trailingIcon != null) {
                        Icon(
                            imageVector = trailingIcon,
                            contentDescription = null,
                            tint = TextHint,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            isError = isError,
            supportingText = if (isError) {
                {
                    Text(
                        text = errorMessage!!,
                        color = ErrorRed,
                        fontSize = 12.sp
                    )
                }
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
                unfocusedContainerColor = Color(0xFFFAFBFF),
                errorContainerColor = Color(0xFFFFF5F5),
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                cursorColor = PrimaryBlue
            )
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Save button with arrow icon on right
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
            .height(54.dp)
            .scale(scale),
        shape = RoundedCornerShape(14.dp),
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
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left spacer for centering
            Spacer(modifier = Modifier.width(24.dp))

            // Center content
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Task,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Text(
                    text = "Simpan Perubahan",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.3.sp
                )
            }

            // Right arrow
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Digital student card (horizontal layout with 4 columns)
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun DigitalStudentCard(profile: MahasiswaProfile?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            UniverMuliaDarkBlue,
                            UniverMuliaDeepBlue
                        )
                    )
                )
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // ── Top stripe ──
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(4.dp)
                            .background(UniverMuliaRed)
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(4.dp)
                            .background(UniverMuliaGold)
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(4.dp)
                            .background(PrimaryBlue)
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(4.dp)
                            .background(UniverMuliaGreen)
                    )
                }

                // ── Header section ──
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                ) {
                    // KARTU DIGITAL label
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = null,
                            tint = UniverMuliaGold,
                            modifier = Modifier.size(18.dp)
                        )
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

                    Spacer(modifier = Modifier.height(4.dp))

                    // Title row
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = null,
                            tint = UniverMuliaGold.copy(alpha = 0.6f),
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            text = "Profil Mahasiswa",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                HorizontalDivider(
                    color = Color.White.copy(alpha = 0.1f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                // ── 4 column data section ──
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CardDataColumn(
                        icon = Icons.Default.PersonOutline,
                        label = "Nama",
                        value = profile?.nama,
                        modifier = Modifier.weight(1f)
                    )
                    VerticalDivider(
                        color = Color.White.copy(alpha = 0.15f),
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )
                    CardDataColumn(
                        icon = Icons.Default.Badge,
                        label = "NIM",
                        value = profile?.nim,
                        modifier = Modifier.weight(1f)
                    )
                    VerticalDivider(
                        color = Color.White.copy(alpha = 0.15f),
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )
                    CardDataColumn(
                        icon = Icons.Default.MenuBook,
                        label = "Program Studi",
                        value = profile?.programStudi,
                        modifier = Modifier.weight(1f)
                    )
                    VerticalDivider(
                        color = Color.White.copy(alpha = 0.15f),
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )
                    CardDataColumn(
                        icon = Icons.Default.AccountBalance,
                        label = "Perguruan Tinggi",
                        value = "Universitas Mulia",
                        valueColor = UniverMuliaGold,
                        modifier = Modifier.weight(1f)
                    )
                }

                HorizontalDivider(
                    color = Color.White.copy(alpha = 0.08f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                // ── Footer – motivational tagline ──
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = "Langkah Kecil\nMenuju Masa Depan Besar",
                        color = UniverMuliaGold.copy(alpha = 0.7f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Italic,
                        fontFamily = FontFamily.Serif,
                        textAlign = TextAlign.End,
                        lineHeight = 16.sp,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
            }
        }
    }
}

@Composable
private fun CardDataColumn(
    icon: ImageVector,
    label: String,
    value: String?,
    valueColor: Color = Color.White,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.5f),
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.6f),
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = value ?: "–",
            color = if (value != null) valueColor else Color.White.copy(alpha = 0.3f),
            fontSize = 11.sp,
            fontWeight = if (value != null) FontWeight.Bold else FontWeight.Normal,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}
