package com.example.dapuribuproject

import com.example.dapuribuproject.DataClass.Post
import org.junit.Test
import org.junit.Assert.assertEquals

class PostUtilTest {
    @Test
    fun filterLongTitles_correctFiltering() {
        //1. Arrange: Siapkan data dummy
        val mockPost = listOf(
            Post(1.toString(), "Short Title", "This is a short title", "aku ganteng", "banget"),
            Post(2.toString(), "This is a very long title for testing purpose", "Aku kamu", "Suka dia", "Tapi dia tidak suka")
        )

        //2. Act: Jalankan fungsi yang diuji
        val result = PostUtil.filterLongTitle(mockPost, 20)

        //3. Assert: Validasi Hasil Akhir
        assertEquals(1, result.size)
        assertEquals("Short Title", result[0].name)
    }
}