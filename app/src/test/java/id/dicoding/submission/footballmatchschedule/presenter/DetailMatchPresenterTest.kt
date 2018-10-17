package id.dicoding.submission.footballmatchschedule.presenter

import com.google.gson.Gson
import id.dicoding.submission.footballmatchschedule.TestContextProvider
import id.dicoding.submission.footballmatchschedule.api.ApiRepository
import id.dicoding.submission.footballmatchschedule.api.TheSportDBApi
import id.dicoding.submission.footballmatchschedule.model.*
import id.dicoding.submission.footballmatchschedule.view_operation.DetailMatchView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class DetailMatchPresenterTest {

        @Mock
        private lateinit var view : DetailMatchView

        private lateinit var presenter: DetailMatchPresenter

        @Mock
        private
        lateinit var gson: Gson

        @Mock
        private
        lateinit var apiRepository: ApiRepository

        @Before
        fun setUp() {
            MockitoAnnotations.initMocks(this)
            presenter = DetailMatchPresenter(view, gson, apiRepository, TestContextProvider())
        }

    @Test
    fun getDetailTeamLogo() {
        val teams : MutableList<Team> = mutableListOf(Team(id = "133604", logo = "https://www.thesportsdb.com/images/media/team/badge/vrtrtp1448813175.png"))
        val response = TeamsResponse(teams)
        val idTeam = "133604"
        val isHomeTeam = false

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamDetailByTeamId(idTeam)),
                TeamsResponse::class.java
        )).thenReturn(response)

        presenter.getDetailTeamLogo(idTeam, isHomeTeam)
        val logo = response.teams[0].logo
        if(isHomeTeam)
            verify(view).showHomeTeamLogo(logo)
        else
            verify(view).showAwayTeamLogo(logo)

    }

    @Test
    fun getDetailMatch() {
        val match : MutableList<Match> = mutableListOf(Match(idEvent = "576548"))
        val response = MatchesResponse(match)
        val idMatch = "576548"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getMatchDetailByMatchId(idMatch)),
                MatchesResponse::class.java
        )).thenReturn(response)

        presenter.getDetailMatch(idMatch)
        verify(view).showLoading()
        verify(view).updateMatch(response.events[0])
        verify(view).hideLoading()
    }
}