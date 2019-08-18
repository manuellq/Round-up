package com.mlcorrea.roundup.framework.extension

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

/**
 * Created by manuel on 17/08/19
 */

const val FORMAT_TIME_BACKEND = "yyyy-MM-dd'T'HH:mm:ssZ"
const val FORMAT_TIME_ZONE ="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

@Throws(Exception::class)
fun Long.parseToDateSting(formatDate: String = "dd-MM-yyyy"): String {
    val cal = Calendar.getInstance(Locale.getDefault())
    cal.timeInMillis = this
    return DateFormat.format(formatDate, cal).toString()
}

@Throws(Exception::class)
fun String.getTime(formatDate: String = FORMAT_TIME_BACKEND): Long {
    val format = SimpleDateFormat(formatDate, Locale.getDefault())

    return format.parse(this).time
}

fun getDateMilliseconds(year: Int, monthOfYear: Int, dayOfMonth: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.set(year, monthOfYear, dayOfMonth, 0, 0)
    return calendar.timeInMillis
}

fun Long.setDateDays(days: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    calendar.add(Calendar.DATE, days)
    return calendar.timeInMillis
}