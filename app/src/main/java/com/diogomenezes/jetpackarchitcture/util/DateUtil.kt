package com.diogomenezes.jetpackarchitcture.util

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*


class DateUtil {

    companion object{

        // dates from server look like this: "2019-07-23T03:28:01.406944Z"
        fun convertServerStringDateToLong(sd: String): Long{
            var stringDate = sd.removeRange(sd.indexOf("T") until sd.length)
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            try {
                return sdf.parse(stringDate).time

            } catch (e: Exception) {
                throw Exception(e)
            }
        }

        fun convertLongToStringDate(longDate: Long): String{
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            try {
                return sdf.format(Date(longDate))

            } catch (e: Exception) {
                throw Exception(e)
            }
        }
    }
}