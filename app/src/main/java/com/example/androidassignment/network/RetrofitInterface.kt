package com.example.androidassignment.network

import com.example.androidassignment.model.AuthToken
import com.example.androidassignment.model.LoginDetails
import com.example.androidassignment.model.MessageDTO
import com.example.androidassignment.model.ThreadDetails
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RetrofitInterface {

    @POST("/api/login")
    fun login(@Body loginDetails: LoginDetails): Observable<AuthToken>

    @GET("/api/messages")
    fun getMessages(@Header("X-Branch-Auth-Token") auth: String): Observable<List<MessageDTO>>

    @POST("/api/messages")
    fun getMessage(
        @Header("X-Branch-Auth-Token") auth: String,
        @Body threadDetails: ThreadDetails
    ): Observable<MessageDTO>
}