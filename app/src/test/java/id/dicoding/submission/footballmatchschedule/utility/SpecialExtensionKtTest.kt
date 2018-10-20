package id.dicoding.submission.footballmatchschedule.utility

import org.junit.Assert.assertEquals
import org.junit.Test

class SpecialExtensionKtTest {

    @Test
    fun parseToIndonesianDate() {
        val date = "2018-10-06"
        val expectedDate = "Sabtu, 6 Oktober 2018"
        assertEquals(expectedDate, date.parseToIndonesianDate())
    }
}