package id.dicoding.submission.footballmatchschedule.presenter

import com.google.gson.Gson
import id.dicoding.submission.footballmatchschedule.TestContextProvider
import id.dicoding.submission.footballmatchschedule.api.ApiRepository
import id.dicoding.submission.footballmatchschedule.api.TheSportDBApi
import id.dicoding.submission.footballmatchschedule.model.Match
import id.dicoding.submission.footballmatchschedule.model.MatchesResponse
import id.dicoding.submission.footballmatchschedule.view_operation.MatchView
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MatchPresenterTest {
    @Mock
    private lateinit var view : MatchView

    private lateinit var presenter: MatchPresenter

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view, gson, apiRepository, TestContextProvider())
    }

    @Test
    fun getPreviousMatchList() {
        val match : MutableList<Match> = mutableListOf()
        val response = MatchesResponse(match)
        val idLeague = "4328"
        val type = "previous"
        val url = TheSportDBApi.getPrevMatchesByLeagueId(idLeague)

        `when`(gson.fromJson(apiRepository
                .doRequest(url),
                MatchesResponse::class.java
        )).thenReturn(response)

        presenter.getMatchList(idLeague, type)
        Mockito.verify(view).showLoading()
        Mockito.verify(view).showMatchList(response.events)
        Mockito.verify(view).hideLoading()
    }

    @Test
    fun getNextMatchList() {
        val match : MutableList<Match> = mutableListOf()
        val response = MatchesResponse(match)
        val idLeague = "4328"
        val type = "next"
        val url = TheSportDBApi.getNextMatchesByLeagueId(idLeague)

        `when`(gson.fromJson(apiRepository
                .doRequest(url),
                MatchesResponse::class.java
        )).thenReturn(response)

        presenter.getMatchList(idLeague, type)
        Mockito.verify(view).showLoading()
        Mockito.verify(view).showMatchList(response.events)
        Mockito.verify(view).hideLoading()
    }

    @After
    fun tearDown() {

    }
}