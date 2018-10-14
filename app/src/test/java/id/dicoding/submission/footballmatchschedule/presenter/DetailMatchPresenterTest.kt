package id.dicoding.submission.footballmatchschedule.presenter

import android.util.Log
import com.google.gson.Gson
import id.dicoding.submission.footballmatchschedule.TestContextProvider
import id.dicoding.submission.footballmatchschedule.api.ApiRepository
import id.dicoding.submission.footballmatchschedule.api.TheSportDBApi
import id.dicoding.submission.footballmatchschedule.model.Match
import id.dicoding.submission.footballmatchschedule.model.MatchesResponse
import id.dicoding.submission.footballmatchschedule.view_operation.DetailMatchView
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
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
    }

    @Test
    fun getDetailMatch() {
        val match : MutableList<Match> = mutableListOf()
        val response = MatchesResponse(match)
        val idMatch = "576548"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getMatchDetailByMatchId(idMatch)),
                MatchesResponse::class.java
        )).thenReturn(response)

        presenter.getDetailMatch(idMatch)
        System.out.println(response.toString())
        verify(view).showLoading()
        verify(view).updateMatch(response.events[0])
        verify(view).hideLoading()
    }
}