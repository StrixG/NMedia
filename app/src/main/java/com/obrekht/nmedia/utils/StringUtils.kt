package com.obrekht.nmedia.utils

object StringUtils {
    fun getCompactNumber(number: Int): String {
        return when {
            number >= 1_000_000 -> {
                "%.1fM".format(number / 1_000_000.0)
            }
            number >= 10000 -> {
                "%dK".format(number / 1000)
            }
            number >= 1000 -> {
                "%.1fK".format(number / 1000.0)
            }
            else -> number.toString()
        }
    }
}