package com.example.androidassignment.network

import com.example.androidassignment.model.AuthToken
import com.example.androidassignment.model.LoginDetails
import com.example.androidassignment.model.MessageDTO
import com.example.androidassignment.model.ThreadDetails
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {
    private val retrofitInterface: RetrofitInterface

    companion object {
        private const val BASE_URL = "https://android-messaging-app-in-2020.herokuapp.com"
        private var apiClient: APIClient? = null

        val instance: APIClient
            get() {
                if (apiClient == null) {
                    apiClient = APIClient()
                }
                return apiClient as APIClient
            }
    }

    init {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()

        retrofitInterface = retrofit.create(RetrofitInterface::class.java)
    }

    fun login(loginDetails: LoginDetails): Observable<AuthToken> =
        retrofitInterface.login(loginDetails)

    fun getMessages(auth: String): Observable<List<MessageDTO>> =
        retrofitInterface.getMessages(auth)

    fun getMessage(auth: String, threadDetails: ThreadDetails): Observable<MessageDTO> =
        retrofitInterface.getMessage(
            auth, threadDetails
        )
}