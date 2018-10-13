package id.dicoding.submission.footballmatchschedule.ui

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.*

class LeagueAdapterUI : AnkoComponent<ViewGroup> {
    lateinit var leagueNameTv: TextView

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)

            leagueNameTv = textView {
                textSize = 16f
            }.lparams {
                margin = dip(16)
            }
        }
    }

}