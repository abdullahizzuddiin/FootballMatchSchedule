package id.dicoding.submission.footballmatchschedule

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import id.dicoding.submission.footballmatchschedule.adapter.LeagueScheduleViewPager
import org.jetbrains.anko.find

class LeagueScheduleActivity : AppCompatActivity(){

    private lateinit var toolbar: Toolbar
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    private lateinit var prevMatchLeagueScheduleFragment : LeagueScheduleFragment
    private lateinit var nextMatchLeagueScheduleFragment : LeagueScheduleFragment

    private lateinit var mIdLeague : String
    private lateinit var mNameLeague : String

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

        val prevMatchExtras = Bundle()
        prevMatchExtras.putInt(resources.getString(R.string.schedule_type_intent_param), 0)
        prevMatchExtras.putString(resources.getString(R.string.id_league_intent_param), mIdLeague)

        prevMatchLeagueScheduleFragment = LeagueScheduleFragment()
        prevMatchLeagueScheduleFragment.arguments = prevMatchExtras

        val nextMatchExtras = Bundle()
        nextMatchExtras.putInt(resources.getString(R.string.schedule_type_intent_param), 1)
        nextMatchExtras.putString(resources.getString(R.string.id_league_intent_param), mIdLeague)

        nextMatchLeagueScheduleFragment = LeagueScheduleFragment()
        nextMatchLeagueScheduleFragment.arguments = nextMatchExtras

        adapter.addFragment(prevMatchLeagueScheduleFragment, "Prev. Match")
        adapter.addFragment(nextMatchLeagueScheduleFragment, "Next Match")
        viewPager.offscreenPageLimit = 2
        viewPager.adapter = adapter
    }
}
