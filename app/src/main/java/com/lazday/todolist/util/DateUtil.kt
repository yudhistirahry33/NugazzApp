package com.lazday.todolist.util

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

private const val FORMAT_TIME = "dd/MM/yyyy hh:mm:ss"

fun dateToString(date: Long?): String? {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = date!!
    return calendar[Calendar.DAY_OF_MONTH].toString() + "/" + (calendar[Calendar.MONTH] + 1) + "/" + calendar[Calendar.YEAR]
}

fun dateToLong(date: String): Long? {
    val dateFormat = SimpleDateFormat(FORMAT_TIME, Locale.getDefault())
    return dateFormat.parse("$date 00:00:00").time
}

fun dateToDialog(context: Context?, datePicker: OnDateSetListener?): DatePickerDialog? {
    val calendar = Calendar.getInstance()
    return DatePickerDialog(
        context!!,
        datePicker,
        calendar[Calendar.YEAR],
        calendar[Calendar.MONTH],
        calendar[Calendar.DAY_OF_MONTH]
    )
}

fun dateToString(year: Int, monthOfYear: Int, dayOfMonth: Int): String? {
    return dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year
}

fun dateToday(): String? {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = System.currentTimeMillis()
    return calendar[Calendar.DAY_OF_MONTH].toString() + "/" + (calendar[Calendar.MONTH] + 1) + "/" + calendar[Calendar.YEAR]
}