package id.dicoding.submission.footballmatchschedule.api

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ApiRepositoryTest {

    @Test
    fun doRequest() {
        val apiRepository = mock(ApiRepository::class.java)
        val url = "Scottish Premier League"

        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }
}