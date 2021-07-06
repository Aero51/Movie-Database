package com.aero51.moviedatabase.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateHelper {

    companion object {

        fun formatDateStringToDefaultLocale(dateString: String, oldDateFormat: String, newDateFormat: String): String {
            val apiFormat = SimpleDateFormat(oldDateFormat)
            var date: Date?= null
            try {
                date = apiFormat.parse(dateString)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val language=Locale.getDefault().getLanguage();
            val defaultLocaleTargetFormat = SimpleDateFormat(newDateFormat, Locale(language, "US"))
            return if(date != null)
                defaultLocaleTargetFormat.format(date)
            else ""
        }

        fun getDefaultLocaleDateFromString(dateString: String,newDateFormat: String): Date {
            val language=Locale.getDefault().getLanguage();
            val defaultLocaleTargetFormat= SimpleDateFormat(newDateFormat, Locale(language, "US"))
            val date = defaultLocaleTargetFormat.parse(dateString)
            return date

        }
        fun getDefaultLocaleStringfromDate(date: Date, newDateFormat: String): String {
            val language=Locale.getDefault().getLanguage();
            var defaultLocaleTargetFormat= SimpleDateFormat(newDateFormat, Locale("en", "US"))
            return defaultLocaleTargetFormat.format(date)
        }


        fun getLargestDate(firstDate: Date, secondDate: Date): Date {
            return if (firstDate.after(secondDate)) firstDate else secondDate
        }

        fun addMonthsToDate(date: Date, monthsNumber: Int): String {
            val defaultLocaleTargetFormat = SimpleDateFormat("dd MMMM yyyy", Locale("nl", "NL"))
            var cal: Calendar = Calendar.getInstance()
            cal.setTime(date)
            cal.add(Calendar.MONTH, monthsNumber)
            return defaultLocaleTargetFormat.format(cal.time)
        }
    }

}