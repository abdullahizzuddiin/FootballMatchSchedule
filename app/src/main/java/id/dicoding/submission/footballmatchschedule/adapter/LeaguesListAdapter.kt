package id.dicoding.submission.footballmatchschedule.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import id.dicoding.submission.footballmatchschedule.R
import id.dicoding.submission.footballmatchschedule.model.League
import org.jetbrains.anko.*

class LeaguesListAdapter(
        private val mLeagues : List<League>,
        private val mListener : (League) -> Unit)
    : RecyclerView.Adapter<LeaguesListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LeagueAdapterUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = mLeagues.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(mLeagues[position], mListener)
    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val leagueName : TextView = itemView.find(R.id.league_name_tv)

        init {
            itemView.setOnClickListener {
                mListener(mLeagues[adapterPosition])
            }
        }

        fun bind(league : League, listener : (League) -> Unit) {
            leagueName.text = league.name
        }
    }

}

class LeagueAdapterUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>) : View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)

            textView {
                id = R.id.league_name_tv
                textSize = 16f
            }.lparams {
                margin = dip(16)
            }
        }
    }

}