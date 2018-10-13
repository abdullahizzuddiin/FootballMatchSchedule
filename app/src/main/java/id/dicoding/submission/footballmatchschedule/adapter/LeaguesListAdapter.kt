package id.dicoding.submission.footballmatchschedule.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import id.dicoding.submission.footballmatchschedule.model.League
import id.dicoding.submission.footballmatchschedule.ui.LeagueAdapterUI
import org.jetbrains.anko.AnkoContext

class LeaguesListAdapter(
        private val mLeagues: List<League>,
        private val mListener: (League) -> Unit)
    : RecyclerView.Adapter<LeaguesListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val leagueAdapterUI = LeagueAdapterUI()
        val view = leagueAdapterUI.createView(AnkoContext.create(parent.context, parent))
        return ViewHolder(view, leagueAdapterUI)
    }

    override fun getItemCount(): Int = mLeagues.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(mLeagues[position]) {
            viewHolder.bind(this)
        }
    }

    inner class ViewHolder(view: View, private val leagueAdapterUI: LeagueAdapterUI) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                mListener(mLeagues[adapterPosition])
            }
        }

        fun bind(league: League) {
            leagueAdapterUI.leagueNameTv.text = league.name
        }
    }

}