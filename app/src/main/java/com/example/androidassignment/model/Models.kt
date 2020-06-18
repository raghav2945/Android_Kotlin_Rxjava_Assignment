package com.example.androidassignment.model

import java.io.Serializable
import java.util.*

data class LoginDetails(
    val username: String,
    val password: String
)

data class ThreadDetails(
    val thread_id: Int,
    val body: String
) : Serializable

data class AuthToken(val auth_token: String) : Serializable

data class MessageDTO(
    val id: Int,
    val thread_id: Int,
    val user_id: Int,
    val agent_id: Int?,
    val body: String,
    val timestamp: String
)

data class Message(
    val id: Int,
    val threadId: Int,
    val userId: Int,
    val agentId: Int?,
    val body: String,
    val timestamp: Date
)