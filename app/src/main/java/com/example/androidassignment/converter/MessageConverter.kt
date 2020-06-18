package com.example.androidassignment.converter

import android.util.Log
import com.example.androidassignment.date.DateFormatter
import com.example.androidassignment.model.Message
import com.example.androidassignment.model.MessageDTO
import java.text.ParseException
import java.util.*
import kotlin.collections.ArrayList

object MessageConverter {
    private const val TAG = "MessageConverter"

    fun convertDtoToModel(messageDTO: MessageDTO): Message {
        val date =
            parseDate(
                messageDTO.timestamp
            )
        return Message(
            messageDTO.id,
            messageDTO.thread_id,
            messageDTO.user_id,
            messageDTO.agent_id,
            messageDTO.body,
            date!!
        )
    }

    fun convertDtoToModel(list: List<MessageDTO>): List<Message> {
        val listOfMessage = ArrayList<Message>()
        list.forEach { it ->
            val message =
                convertDtoToModel(
                    it
                )
            listOfMessage.add(message)
        }
        return listOfMessage
    }

    private fun parseDate(timeStamp: String): Date? {
        var date: Date? = null
        try {
            date = DateFormatter.inputDateFormat().parse(timeStamp)
        } catch (exception: ParseException) {
            Log.e(TAG, "Error at parsing the date")
        }
        return date
    }
}