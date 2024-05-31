package com.example.userinfocollector.domain.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object UserUtils {
    fun getFormattedDateFormLong(timestamp: Long) : String{
        return  SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(timestamp))
    }
}