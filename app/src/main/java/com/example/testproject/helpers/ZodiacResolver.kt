package com.example.testproject.helpers

import android.icu.util.Calendar
import java.util.Date

fun resolveZodiac(date: Date): String {
    val calendar = Calendar.getInstance()
    calendar.time = date
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val zodiacCode = month * 100 + day
    val zodiacRanges = mutableMapOf<String, IntRange>()
    zodiacRanges["Capricornus"] = 0..(0 * 100 + 19)
    zodiacRanges["Aquarius"] = (0 * 100 + 20)..(1 * 100 + 18)
    zodiacRanges["Pisces"] = (1 * 100 + 19)..(2 * 100 + 20)
    zodiacRanges["Aries"] = (2 * 100 + 21)..(3 * 100 + 19)
    zodiacRanges["Taurus"] = (3 * 100 + 20)..(4 * 100 + 20)
    zodiacRanges["Gemini"] = (4 * 100 + 21)..(5 * 100 + 20)
    zodiacRanges["Cancer"] = (5 * 100 + 21)..(6 * 100 + 22)
    zodiacRanges["Leo"] = (6 * 100 + 23)..(7 * 100 + 22)
    zodiacRanges["Virgo"] = (7 * 100 + 23)..(8 * 100 + 22)
    zodiacRanges["Libra"] = (8 * 100 + 23)..(9 * 100 + 22)
    zodiacRanges["Scorpius"] = (9 * 100 + 23)..(10 * 100 + 21)
    zodiacRanges["Sagittarius"] = (10 * 100 + 22)..(11 * 100 + 21)
    zodiacRanges["Capricornus"] = (12 * 100 + 22)..(12 * 100 + 31)

    zodiacRanges.forEach { (zodiac, codeRange) ->
        if (zodiacCode in codeRange) {
            return zodiac
        }
    }

    return ""
}