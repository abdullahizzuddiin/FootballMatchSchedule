package id.dicoding.submission.footballmatchschedule

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import id.dicoding.submission.footballmatchschedule.db.database
import id.dicoding.submission.footballmatchschedule.model.FavoriteMatch.Companion.AWAY_TEAM_NAME
import id.dicoding.submission.footballmatchschedule.model.FavoriteMatch.Companion.AWAY_TEAM_SCORE
import id.dicoding.submission.footballmatchschedule.model.FavoriteMatch.Companion.HOME_TEAM_NAME
import id.dicoding.submission.footballmatchschedule.model.FavoriteMatch.Companion.HOME_TEAM_SCORE
import id.dicoding.submission.footballmatchschedule.model.FavoriteMatch.Companion.LEAGUE_ID
import id.dicoding.submission.footballmatchschedule.model.FavoriteMatch.Companion.MATCH_DATE
import id.dicoding.submission.footballmatchschedule.model.FavoriteMatch.Companion.MATCH_ID
import id.dicoding.submission.footballmatchschedule.model.FavoriteMatch.Companion.TABLE_FAVORITE_MATCH
import id.dicoding.submission.footballmatchschedule.model.Match
import id.dicoding.submission.footballmatchschedule.presenter.DetailMatchPresenter
import id.dicoding.submission.footballmatchschedule.utility.load
import id.dicoding.submission.footballmatchschedule.utility.parseToIndonesianDate
import id.dicoding.submission.footballmatchschedule.utility.setInvisible
import id.dicoding.submission.footballmatchschedule.utility.setVisible
import id.dicoding.submission.footballmatchschedule.view_operation.DetailMatchView
import kotlinx.android.synthetic.main.activity_detail_match.*
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.design.snackbar

class DetailMatchActivity : AppCompatActivity(), DetailMatchView {
    private var menuItem: Menu? = null

    private var isFavorite: Boolean = false

    private lateinit var mMatch: Match

    private lateinit var mIdMatch: String

    private lateinit var mPresenter: DetailMatchPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)
        receiveExtras()
        initPresenter()
    }

    private fun requestTeamLogo(match: Match) {
        match.idHomeTeam?.let {
            mPresenter.getDetailTeamLogo(it, true)
        }
        match.idAwayTeam?.let {
            mPresenter.getDetailTeamLogo(it, false)
        }
    }

    private fun initPresenter() {
        mPresenter = DetailMatchPresenter(this)
        mPresenter.getDetailMatch(mIdMatch)
    }

    private fun receiveExtras() {
        val bundle = intent.extras
        mIdMatch = bundle?.getString(resources.getString(R.string.id_match_intent_param)) ?: ""
        isFavorite = bundle?.getBoolean(resources.getString(R.string.is_favorite_intent_param)) ?: false
    }

    override fun showHomeTeamLogo(url: String?) {
        detail_home_team_logo_iv.load(url)
    }

    override fun showAwayTeamLogo(url: String?) {
        detail_away_team_logo_iv.load(url)
    }

    override fun showLoading() {
        container.setInvisible()
        detail_pb.setVisible()
    }

    override fun hideLoading() {
        container.setVisible()
        detail_pb.setInvisible()
    }

    override fun updateMatch(data: Match) {
        showDetailMatch(data)
        requestTeamLogo(data)
        mMatch = data.copy()
    }

    private fun showDetailMatch(match: Match) {
        detail_date_tv.text = match.date?.parseToIndonesianDate()
        detail_home_team_name_tv.text = match.homeTeam
        detail_home_score_tv.text = match.homeScore?.toString()
        detail_home_shot_tv.text = match.homeShots?.toString()
        detail_home_goal_tv.text = match.homeGoalDetails
        detail_home_formation_tv.text = match.homeFormation
        detail_goalkeeper_home_name_tv.text = match.homeLineupGoalkeeper
        detail_defense_home_name_tv.text = match.homeLineupDefense
        detail_midfielder_home_name_tv.text = match.homeLineupMidfield
        detail_forward_home_name_tv.text = match.homeLineupForward
        detail_substitute_home_name_tv.text = match.homeLineupSubstitutes

        detail_away_team_name_tv.text = match.awayTeam
        detail_away_score_tv.text = match.awayScore?.toString()
        detail_away_shot_tv.text = match.awayShots?.toString()
        detail_away_goal_tv.text = match.awayGoalDetails
        detail_away_formation_tv.text = match.awayFormation
        detail_goalkeeper_away_name_tv.text = match.awayLineupGoalkeeper
        detail_defense_away_name_tv.text = match.awayLineupDefense
        detail_midfielder_away_name_tv.text = match.awayLineupMidfield
        detail_forward_away_name_tv.text = match.awayLineupForward
        detail_substitute_away_name_tv.text = match.awayLineupSubstitutes
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        updateFavoriteIcon()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                onFavoriteMenuClicked()
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onFavoriteMenuClicked(): Boolean {
        if (isFavorite)
            removeFromFavorite()
        else
            addToFavorite()

        toggleFavoriteStatus()
        updateFavoriteIcon()

        return true
    }

    private fun toggleFavoriteStatus() {
        isFavorite = !isFavorite
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(TABLE_FAVORITE_MATCH,
                        MATCH_ID to mMatch.idEvent,
                        LEAGUE_ID to mMatch.idLeague,
                        HOME_TEAM_NAME to mMatch.homeTeam,
                        AWAY_TEAM_NAME to mMatch.awayTeam,
                        HOME_TEAM_SCORE to mMatch.homeScore,
                        AWAY_TEAM_SCORE to mMatch.awayScore,
                        MATCH_DATE to mMatch.date)
            }
            scroll.snackbar(resources.getString(R.string.added_to_favorite)).show()
        } catch (e: SQLiteConstraintException) {
            scroll.snackbar(e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            mMatch.idEvent?.let {
                database.use {
                    delete(
                            TABLE_FAVORITE_MATCH,
                            "(MATCH_ID = {id})",
                            "id" to it)
                }
                scroll.snackbar(resources.getString(R.string.remove_from_favorite)).show()
            }
        } catch (e: SQLiteConstraintException) {
            scroll.snackbar(e.localizedMessage).show()
        }
    }

    private fun updateFavoriteIcon() {
        val favoriteIcon = if (isFavorite) R.drawable.ic_added_to_favorites else R.drawable.ic_add_to_favorites
        menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, favoriteIcon)
    }
}
