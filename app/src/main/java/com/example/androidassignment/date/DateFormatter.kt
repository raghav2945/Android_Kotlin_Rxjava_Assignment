package com.example.androidassignment.date

import java.text.SimpleDateFormat

object DateFormatter {
    private const val DATE_INCOMING_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private const val DATE_OUTPUT_FORMAT = "dd MMM yyyy HH:mm:ss"

    fun inputDateFormat(): SimpleDateFormat = SimpleDateFormat(DATE_INCOMING_FORMAT)
    fun outputDateFormat(): SimpleDateFormat = SimpleDateFormat(DATE_OUTPUT_FORMAT)
}