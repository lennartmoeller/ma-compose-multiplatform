package com.lennartmoeller.ma.composemultiplatform.util

import kotlin.math.roundToInt

class Euro {
    companion object {
        fun toStr(
            cent: Int,
            includeEuroSign: Boolean = true,
            includeDots: Boolean = true
        ): String {
            val isNegative = cent < 0
            val absCent = if (isNegative) -cent else cent
            val prefix: String = if (isNegative) "-" else ""
            var euroStr: String = (absCent / 100).toString()
            if (includeDots) {
                euroStr = euroStr.reversed().chunked(3).joinToString(".").reversed()
            }
            val centsStr: String = Utility.addLeadingZeros(absCent % 100, 2)
            return "$prefix$euroStr,$centsStr${if (includeEuroSign) " €" else ""}"
        }

        fun toCent(formattedString: String): Int? {
            val numberString = formattedString
                .replace(" ", "")
                .replace("€", "")
                .replace(".", "")
                .replace(",", ".")
            if (numberString == "") {
                return 0
            }
            val euroDouble = numberString.toDoubleOrNull() ?: return null
            return (euroDouble * 100).roundToInt()
        }
    }
}
