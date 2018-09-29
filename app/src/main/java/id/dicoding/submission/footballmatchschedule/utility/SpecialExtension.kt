package id.dicoding.submission.footballmatchschedule.utility

import android.view.View
import id.dicoding.submission.footballmatchschedule.constant.Constant
import java.text.SimpleDateFormat
import java.util.*

fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setInvisible() {
    visibility = View.GONE
}

fun String.parseToIndonesianDate() : String {
    //goal format: Hari, Tanggal Bulan Tahun
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(this)
    val calendar = Calendar.getInstance()
    calendar.time = date

    val dayInIndonesian = Constant.DAY_IN_INDONESIAN[calendar[Calendar.DAY_OF_WEEK]-1]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
    val monthInIndonesian = Constant.MONT_IN_INDONESIAN[calendar[Calendar.MONTH]]
    val year = calendar[Calendar.YEAR]

    return "$dayInIndonesian, $dayOfMonth $monthInIndonesian $year"
}