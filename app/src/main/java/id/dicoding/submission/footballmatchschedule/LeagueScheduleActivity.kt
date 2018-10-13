package id.dicoding.submission.footballmatchschedule

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import id.dicoding.submission.footballmatchschedule.adapter.LeagueScheduleViewPager
import org.jetbrains.anko.find

class LeagueScheduleActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    private lateinit var prevMatchMatchesFragment: MatchesFragment
    private lateinit var nextMatchMatchesFragment: MatchesFragment
    private lateinit var favoriteMatchMatchesFragment: FavoriteMatchesFragment

    private lateinit var mIdLeague: String
    private lateinit var mNameLeague: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league_schedule)
        receiveExtras()
        initView()
    }

    private fun receiveExtras() {
        val intent = intent
        mIdLeague = intent.getStringExtra(resources.getString(R.string.id_league_intent_param))
        mNameLeague = intent.getStringExtra(resources.getString(R.string.name_league_intent_param))
    }

    private fun initView() {
        toolbar = find(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = "Jadwal $mNameLeague"

        viewPager = find(R.id.viewpager)
        setupViewPager(viewPager)

        tabLayout = find(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = LeagueScheduleViewPager(supportFragmentManager)

        adapter.addFragment(initPrevMatchFragment(), resources.getString(R.string.previous_match_label))
        adapter.addFragment(initNextMatchFragment(), resources.getString(R.string.next_match_label))
        adapter.addFragment(initFavoriteMatchFragment(), resources.getString(R.string.favorite_match_label))
        viewPager.offscreenPageLimit = 3
        viewPager.adapter = adapter
    }

    private fun initPrevMatchFragment(): MatchesFragment {
        val prevMatchExtras = Bundle()
        prevMatchExtras.putString(resources.getString(R.string.match_type_intent_param),
                resources.getString(R.string.match_type_prev_match_intent_param))
        prevMatchExtras.putString(resources.getString(R.string.id_league_intent_param), mIdLeague)

        prevMatchMatchesFragment = MatchesFragment()
        prevMatchMatchesFragment.arguments = prevMatchExtras

        return prevMatchMatchesFragment
    }

    private fun initNextMatchFragment(): MatchesFragment {
        val nextMatchExtras = Bundle()
        nextMatchExtras.putString(resources.getString(R.string.match_type_intent_param),
                resources.getString(R.string.match_type_next_match_intent_param))
        nextMatchExtras.putString(resources.getString(R.string.id_league_intent_param), mIdLeague)

        nextMatchMatchesFragment = MatchesFragment()
        nextMatchMatchesFragment.arguments = nextMatchExtras

        return nextMatchMatchesFragment
    }

    private fun initFavoriteMatchFragment(): FavoriteMatchesFragment {
        val favoriteMatchExtras = Bundle()
        favoriteMatchExtras.putString(resources.getString(R.string.id_league_intent_param), mIdLeague)

        favoriteMatchMatchesFragment = FavoriteMatchesFragment()
        favoriteMatchMatchesFragment.arguments = favoriteMatchExtras

        return favoriteMatchMatchesFragment
    }
}
