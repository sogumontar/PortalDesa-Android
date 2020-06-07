package com.PortalDesa.data.support

import android.util.Log
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
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

    fun formatDate(dateTime: String?, format: String?, originFormat: String?): String {
        val fmt = SimpleDateFormat(originFormat)
        var date: Date? = null
        try {
            date = fmt.parse(dateTime)
        } catch (e: ParseException) {
            Log.e("formatDate: err:", e.message)
            e.printStackTrace()
        }
        val fmtOut = SimpleDateFormat(format)
        return fmtOut.format(date)
    }
}