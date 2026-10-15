package com.example.profilmahasiswa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * Model data profil mahasiswa yang telah tersimpan
 */
data class MahasiswaProfile(
    val nama: String,
    val nim: String,
    val email: String,
    val programStudi: String,
    val semester: Int,
    val isAktif: Boolean
)

/**
 * ViewModel yang mengelola State, Event, dan logika validasi profil mahasiswa.
 * Konsep Unidirectional Data Flow (UDF):
 * - State mengalir ke bawah (ke UI Composable)
 * - Event mengalir ke atas (dari interaksi UI ke ViewModel)
 */
class ProfilViewModel : ViewModel() {

    // ── STATE FORM ─────────────────────────────────────────────────────────────

    // 1. Nama Mahasiswa
    var namaInput by mutableStateOf("")
        private set

    // 2. NIM (Hanya angka)
    var nimInput by mutableStateOf("")
        private set

    // 3. Email (Validasi format)
    var emailInput by mutableStateOf("")
        private set

    // 4. Program Studi (Pilihan Chip)
    var prodiInput by mutableStateOf("")
        private set

    // 5. Semester (Slider 1 sampai 8)
    var semesterInput by mutableFloatStateOf(1f)
        private set

    // 6. Status Mahasiswa Aktif (Switch)
    var isAktifInput by mutableStateOf(true)
        private set

    // ── STATE INTERAKSI & VALIDASI ─────────────────────────────────────────────

    var namaTouched by mutableStateOf(false)
        private set

    var nimTouched by mutableStateOf(false)
        private set

    var emailTouched by mutableStateOf(false)
        private set

    // State profil tersimpan & Banner Sukses
    var savedProfile by mutableStateOf<MahasiswaProfile?>(null)
        private set

    var showSuccessBanner by mutableStateOf(false)
        private set

    // Daftar Program Studi yang tersedia
    val daftarProdi = listOf(
        "Teknik Informatika",
        "Sistem Informasi",
        "Teknologi Informasi",
        "Teknik Elektro",
        "Bisnis Digital"
    )

    // ── LOGIKA VALIDASI ────────────────────────────────────────────────────────

    // Regex standar untuk validasi format email
    private val emailPattern = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")

    val isNamaValid: Boolean
        get() = namaInput.trim().length >= 3

    val isNimValid: Boolean
        get() = nimInput.isNotBlank() && nimInput.all { it.isDigit() } && nimInput.length in 7..12

    val isEmailValid: Boolean
        get() = emailInput.isNotBlank() && emailPattern.matches(emailInput.trim())

    val isProdiValid: Boolean
        get() = prodiInput.isNotBlank()

    // Pesan error jika field sudah disentuh (touched) tapi belum valid
    val namaError: String?
        get() = when {
            !namaTouched -> null
            namaInput.isBlank() -> "Nama lengkap tidak boleh kosong"
            namaInput.trim().length < 3 -> "Nama minimal 3 karakter"
            else -> null
        }

    val nimError: String?
        get() = when {
            !nimTouched -> null
            nimInput.isBlank() -> "NIM tidak boleh kosong"
            !nimInput.all { it.isDigit() } -> "NIM hanya boleh berisi angka"
            nimInput.length < 7 -> "NIM minimal 7 digit angka"
            nimInput.length > 12 -> "NIM maksimal 12 digit"
            else -> null
        }

    val emailError: String?
        get() = when {
            !emailTouched -> null
            emailInput.isBlank() -> "Email tidak boleh kosong"
            !emailPattern.matches(emailInput.trim()) -> "Format email tidak valid (contoh: dewi@mhs.ac.id)"
            else -> null
        }

    // Perhitungan persentase kelengkapan (0 - 100%)
    val completionPercentage: Float
        get() {
            var completedCount = 0
            if (isNamaValid) completedCount++
            if (isNimValid) completedCount++
            if (isEmailValid) completedCount++
            if (isProdiValid) completedCount++
            if (semesterInput in 1f..8f) completedCount++
            return (completedCount / 5f)
        }

    // Form dinyatakan lengkap jika semua field valid 100%
    val isFormComplete: Boolean
        get() = isNamaValid && isNimValid && isEmailValid && isProdiValid

    // Hitung inisial untuk avatar kartu pratinjau (misal: "Dewi Nofitta Sari" -> "DN")
    val inisialNama: String
        get() {
            val trimmed = namaInput.trim()
            if (trimmed.isBlank()) return "??"
            val words = trimmed.split("\\s+".toRegex()).filter { it.isNotBlank() }
            return when {
                words.size >= 2 -> "${words[0].first().uppercaseChar()}${words[1].first().uppercaseChar()}"
                words[0].length >= 2 -> words[0].take(2).uppercase()
                else -> words[0].first().uppercaseChar().toString()
            }
        }

    // ── EVENT HANDLERS (Aksi Pengguna) ─────────────────────────────────────────

    fun onNamaChange(newValue: String) {
        namaInput = newValue
        namaTouched = true
        showSuccessBanner = false
    }

    fun onNimChange(newValue: String) {
        // Filter: hanya izinkan karakter angka
        if (newValue.all { it.isDigit() } && newValue.length <= 12) {
            nimInput = newValue
            nimTouched = true
            showSuccessBanner = false
        }
    }

    fun onEmailChange(newValue: String) {
        emailInput = newValue
        emailTouched = true
        showSuccessBanner = false
    }

    fun onProdiSelect(selected: String) {
        prodiInput = selected
        showSuccessBanner = false
    }

    fun onSemesterChange(newValue: Float) {
        semesterInput = newValue
        showSuccessBanner = false
    }

    fun onAktifChange(newValue: Boolean) {
        isAktifInput = newValue
        showSuccessBanner = false
    }

    fun simpanProfil() {
        namaTouched = true
        nimTouched = true
        emailTouched = true

        if (isFormComplete) {
            savedProfile = MahasiswaProfile(
                nama = namaInput.trim(),
                nim = nimInput.trim(),
                email = emailInput.trim(),
                programStudi = prodiInput,
                semester = semesterInput.toInt(),
                isAktif = isAktifInput
            )
            showSuccessBanner = true
        }
    }

    fun tutupBanner() {
        showSuccessBanner = false
    }

    fun resetForm() {
        namaInput = ""
        nimInput = ""
        emailInput = ""
        prodiInput = ""
        semesterInput = 1f
        isAktifInput = true
        namaTouched = false
        nimTouched = false
        emailTouched = false
        savedProfile = null
        showSuccessBanner = false
    }

    /**
     * Memudahkan pengisian data otomatis dengan data mahasiswa:
     * Dewi Nofitta Sari (NIM: 2411065)
     */
    fun isiDataDewi() {
        namaInput = "Dewi Nofitta Sari"
        nimInput = "2411065"
        emailInput = "dewi.nofitta@mhs.ac.id"
        prodiInput = "Sistem Informasi"
        semesterInput = 4f
        isAktifInput = true
        namaTouched = true
        nimTouched = true
        emailTouched = true
        showSuccessBanner = false
    }
}
