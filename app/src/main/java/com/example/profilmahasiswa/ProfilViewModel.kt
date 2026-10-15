package com.example.profilmahasiswa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * Data class representing a saved student profile.
 */
data class MahasiswaProfile(
    val nama: String,
    val nim: String,
    val programStudi: String
)

/**
 * ViewModel that holds form state, validation logic, and the saved profile.
 */
class ProfilViewModel : ViewModel() {

    // ── Form field states ──────────────────────────────────────────────────────

    var namaInput by mutableStateOf("")
        private set

    var nimInput by mutableStateOf("")
        private set

    var prodiInput by mutableStateOf("")
        private set

    // ── Touched flags (show errors only after user interacts with a field) ─────

    var namaTouched by mutableStateOf(false)
        private set

    var nimTouched by mutableStateOf(false)
        private set

    var prodiTouched by mutableStateOf(false)
        private set

    // ── Saved profile ──────────────────────────────────────────────────────────

    var savedProfile by mutableStateOf<MahasiswaProfile?>(null)
        private set

    // ── Snackbar trigger ───────────────────────────────────────────────────────

    var showSnackbar by mutableStateOf(false)
        private set

    // ── Validation ─────────────────────────────────────────────────────────────

    val namaError: String?
        get() = when {
            !namaTouched -> null
            namaInput.isBlank() -> "Nama lengkap tidak boleh kosong"
            namaInput.trim().length < 3 -> "Nama harus minimal 3 karakter"
            !namaInput.trim().matches(Regex("^[a-zA-Z .'-]+\$")) ->
                "Nama hanya boleh berisi huruf dan spasi"
            else -> null
        }

    val nimError: String?
        get() = when {
            !nimTouched -> null
            nimInput.isBlank() -> "NIM tidak boleh kosong"
            !nimInput.matches(Regex("^[0-9]+\$")) -> "NIM hanya boleh berisi angka"
            nimInput.length < 7 -> "NIM harus minimal 7 digit"
            nimInput.length > 12 -> "NIM tidak boleh lebih dari 12 digit"
            else -> null
        }

    val prodiError: String?
        get() = when {
            !prodiTouched -> null
            prodiInput.isBlank() -> "Program studi tidak boleh kosong"
            prodiInput.trim().length < 3 -> "Program studi harus minimal 3 karakter"
            else -> null
        }

    val isFormValid: Boolean
        get() = namaInput.trim().length >= 3 &&
                namaInput.trim().matches(Regex("^[a-zA-Z .'-]+\$")) &&
                nimInput.matches(Regex("^[0-9]{7,12}\$")) &&
                prodiInput.trim().length >= 3

    // ── Event handlers ─────────────────────────────────────────────────────────

    fun onNamaChange(value: String) {
        namaInput = value
        namaTouched = true
    }

    fun onNimChange(value: String) {
        // Only allow digits input
        if (value.all { it.isDigit() }) {
            nimInput = value
        }
        nimTouched = true
    }

    fun onProdiChange(value: String) {
        prodiInput = value
        prodiTouched = true
    }

    fun onNamaFocusLost() { namaTouched = true }
    fun onNimFocusLost()   { nimTouched = true }
    fun onProdiFocusLost() { prodiTouched = true }

    fun simpanData() {
        // Mark all fields as touched to trigger validation display
        namaTouched = true
        nimTouched = true
        prodiTouched = true

        if (isFormValid) {
            savedProfile = MahasiswaProfile(
                nama = namaInput.trim(),
                nim = nimInput.trim(),
                programStudi = prodiInput.trim()
            )
            showSnackbar = true
        }
    }

    fun onSnackbarDismissed() {
        showSnackbar = false
    }
}
