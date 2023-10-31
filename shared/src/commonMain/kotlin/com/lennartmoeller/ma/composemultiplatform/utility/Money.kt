package com.lennartmoeller.ma.composemultiplatform.utility

class Money {
    companion object {
        fun formatCents(cent: Int): String {
            val isNegative = cent < 0
            val absCent = if (isNegative) -cent else cent
            val prefix: String = if (isNegative) "-" else ""
            val euroStr: String =
                (absCent / 100).toString().reversed().chunked(3).joinToString(".").reversed()
            val centsStr: String = Utility.addLeadingZeros(absCent % 100, 2)
            return "$prefix$euroStr,$centsStr €"
        }
    }
}
