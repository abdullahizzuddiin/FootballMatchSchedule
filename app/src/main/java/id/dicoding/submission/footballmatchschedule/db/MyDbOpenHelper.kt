package id.dicoding.submission.footballmatchschedule.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import id.dicoding.submission.footballmatchschedule.model.FavoriteMatch
import org.jetbrains.anko.db.*

class MyDbOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "FavoriteMatch.db", null, 1) {
    companion object {
        private var instance: MyDbOpenHelper? = null

        fun getInstance(context: Context): MyDbOpenHelper {
            if (instance == null) {
                instance = MyDbOpenHelper(context.applicationContext)
            }

            return instance as MyDbOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(FavoriteMatch.TABLE_FAVORITE_MATCH, true,
                FavoriteMatch.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteMatch.MATCH_ID to TEXT + UNIQUE,
                FavoriteMatch.LEAGUE_ID to TEXT,
                FavoriteMatch.HOME_TEAM_NAME to TEXT,
                FavoriteMatch.HOME_TEAM_SCORE to INTEGER,
                FavoriteMatch.AWAY_TEAM_NAME to TEXT,
                FavoriteMatch.AWAY_TEAM_SCORE to INTEGER,
                FavoriteMatch.MATCH_DATE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoriteMatch.TABLE_FAVORITE_MATCH, true)
    }

}

val Context.database: MyDbOpenHelper
    get() = MyDbOpenHelper.getInstance(applicationContext)