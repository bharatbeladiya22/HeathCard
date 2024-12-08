package com.healthcard.util

import android.icu.util.Calendar

fun Calendar.greetings(): String {
    val currentHour = get(Calendar.HOUR_OF_DAY)
    return when (currentHour) {
        in 6..11 -> "Good morning"
        in 12..17 -> "Good afternoon"
        in 18..23 -> "Good evening"
        else -> "Good night"
    }
}