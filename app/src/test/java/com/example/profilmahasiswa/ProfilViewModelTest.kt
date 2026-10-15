package com.example.profilmahasiswa

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Unit Test untuk ProfilViewModel guna memverifikasi State, Event, dan Logika Validasi.
 */
class ProfilViewModelTest {

    private lateinit var viewModel: ProfilViewModel

    @Before
    fun setUp() {
        viewModel = ProfilViewModel()
    }

    @Test
    fun `test initial state is empty and incomplete`() {
        assertEquals("", viewModel.namaInput)
        assertEquals("", viewModel.nimInput)
        assertEquals("", viewModel.emailInput)
        assertEquals("", viewModel.prodiInput)
        assertEquals(1f, viewModel.semesterInput, 0.01f)
        assertTrue(viewModel.isAktifInput)
        assertFalse(viewModel.isFormComplete)
        assertNull(viewModel.savedProfile)
        assertFalse(viewModel.showSuccessBanner)
    }

    @Test
    fun `test onNimChange only accepts digits`() {
        viewModel.onNimChange("2411065")
        assertEquals("2411065", viewModel.nimInput)

        // Mencoba input teks huruf atau simbol tidak boleh mengubah state nim
        viewModel.onNimChange("2411abc")
        assertEquals("2411065", viewModel.nimInput)
    }

    @Test
    fun `test email validation logic`() {
        viewModel.onEmailChange("dewi.invalid")
        assertFalse(viewModel.isEmailValid)

        viewModel.onEmailChange("dewi.nofitta@mhs.ac.id")
        assertTrue(viewModel.isEmailValid)
    }

    @Test
    fun `test inisial nama generation`() {
        viewModel.onNamaChange("Dewi Nofitta Sari")
        assertEquals("DN", viewModel.inisialNama)

        viewModel.onNamaChange("Dewi")
        assertEquals("DE", viewModel.inisialNama)

        viewModel.onNamaChange("")
        assertEquals("??", viewModel.inisialNama)
    }

    @Test
    fun `test isiDataDewi and save profile`() {
        viewModel.isiDataDewi()

        assertEquals("Dewi Nofitta Sari", viewModel.namaInput)
        assertEquals("2411065", viewModel.nimInput)
        assertEquals("dewi.nofitta@mhs.ac.id", viewModel.emailInput)
        assertEquals("Sistem Informasi", viewModel.prodiInput)
        assertEquals(4f, viewModel.semesterInput, 0.01f)
        assertTrue(viewModel.isAktifInput)
        assertEquals(1.0f, viewModel.completionPercentage, 0.01f)
        assertTrue(viewModel.isFormComplete)

        // Simpan profil
        viewModel.simpanProfil()
        assertTrue(viewModel.showSuccessBanner)
        assertNotNull(viewModel.savedProfile)

        val profile = viewModel.savedProfile!!
        assertEquals("Dewi Nofitta Sari", profile.nama)
        assertEquals("2411065", profile.nim)
        assertEquals("dewi.nofitta@mhs.ac.id", profile.email)
        assertEquals("Sistem Informasi", profile.programStudi)
        assertEquals(4, profile.semester)
        assertTrue(profile.isAktif)
    }
}
