package com.aero51.moviedatabase.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException
import java.util.*

object CurrencyConverter {

    fun currencyFormat(amount: Double): String {
        val otherSymbols = DecimalFormatSymbols(Locale.GERMANY)
        otherSymbols.decimalSeparator = ','
        otherSymbols.groupingSeparator = '.'

        val formatter = DecimalFormat("$###,###", otherSymbols)
        return formatter.format(amount)
    }

    fun stringToDouble(amount: String?): Double? {
        val otherSymbols = DecimalFormatSymbols(Locale.GERMANY)
        otherSymbols.decimalSeparator = ','
        otherSymbols.groupingSeparator = '.'

        return try {
            val formatter = DecimalFormat("###,###,##0.00", otherSymbols)
            formatter.parse(amount).toDouble()
        } catch (e: ParseException){
            null
        }
    }

    fun doubleToString(amount: Double?) : String {
        amount?.let {
            val otherSymbols = DecimalFormatSymbols(Locale.GERMANY)
            otherSymbols.decimalSeparator = ','
            otherSymbols.groupingSeparator = '.'

            val df = DecimalFormat("#,##0.00", otherSymbols)
            df.isDecimalSeparatorAlwaysShown = true
            return df.format(amount)
        } ?: run {
            return ""
        }
    }
}