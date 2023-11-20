package com.lennartmoeller.ma_compose_multiplatform.util

class Utility {
    companion object {
        fun addLeadingZeros(amount: Int, length: Int): String {
            val amountStr = amount.toString()
            if (amountStr.length > length) {
                throw IllegalArgumentException("Length is smaller than the string representation of amount.")
            }
            return amountStr.padStart(length, '0')
        }
    }
}
