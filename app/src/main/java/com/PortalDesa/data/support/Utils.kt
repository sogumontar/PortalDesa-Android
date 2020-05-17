package com.PortalDesa.data.support

import java.text.NumberFormat
import java.util.*

class Utils {
    fun numberToIDR(number: Int, isWithSymbol: Boolean): String? {
        val numberFormat =
            NumberFormat.getInstance(Locale.GERMANY)
        numberFormat.maximumFractionDigits = 4
        val numberIDR = numberFormat.format(number.toLong())
        return if (isWithSymbol) {
            "Rp $numberIDR"
        } else {
            numberIDR
        }
    }
}