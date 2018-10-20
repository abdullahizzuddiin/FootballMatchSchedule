package id.dicoding.submission.footballmatchschedule.presenter

import com.google.gson.Gson
import id.dicoding.submission.footballmatchschedule.TestContextProvider
import id.dicoding.submission.footballmatchschedule.api.ApiRepository
import id.dicoding.submission.footballmatchschedule.api.TheSportDBApi
import id.dicoding.submission.footballmatchschedule.model.League
import id.dicoding.submission.footballmatchschedule.model.LeaguesResponse
import id.dicoding.submission.footballmatchschedule.view_operation.LeagueView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class LeaguePresenterTest {
    @Mock
    private lateinit var view: LeagueView

    private lateinit var presenter: LeaguePresenter

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LeaguePresenter(view, gson, apiRepository, TestContextProvider())
    }

    @Test
    fun getLeagueList() {
        val leagues: MutableList<League> = mutableListOf(League(id = "1", sportType = "Baseball"), League(id = "2", sportType = "Soccer"))
        val response = LeaguesResponse(leagues)
        val filteredData = mutableListOf(League("2", sportType = "Soccer"))

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getLeagues()),
                LeaguesResponse::class.java
        )).thenReturn(response)

        presenter.getLeagueList()
        Mockito.verify(view).showLoading()
        Mockito.verify(view).showLeagueList(filteredData)
        Mockito.verify(view).hideLoading()
    }
}