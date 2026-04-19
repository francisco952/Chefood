package com.gold.chefood.adapters

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class DayItem(
    val dayName: String,
    val dayNumber: Int,
    val isToday: Boolean
)

fun getCurrentMonthDays(): List<DayItem> {

    val list = mutableListOf<DayItem>()

    val calendar = Calendar.getInstance()

    val currentDay =
        calendar.get(Calendar.DAY_OF_MONTH)

    val currentMonth =
        calendar.get(Calendar.MONTH)

    val currentYear =
        calendar.get(Calendar.YEAR)

    val totalDays =
        calendar.getActualMaximum(
            Calendar.DAY_OF_MONTH
        )

    for (i in 1..totalDays) {

        val itemCalendar = Calendar.getInstance()

        itemCalendar.set(
            currentYear,
            currentMonth,
            i
        )

        val dayName =
            SimpleDateFormat(
                "EEE",
                Locale.getDefault()
            ).format(itemCalendar.time)

        list.add(
            DayItem(
                dayName = dayName.uppercase(),
                dayNumber = i,
                isToday = i == currentDay
            )
        )
    }

    return list
}