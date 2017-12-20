package com.lego.myyalantistask.core

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.lego.myyalantistask.repository.db.Result

class RequestController {

    private val redditUrl: String = "https://www.reddit.com/"
    val redditApi: RedditApi

    init {
        val typeToken = object : TypeToken<Result>() {}.type
        val gson = GsonBuilder()
                .registerTypeAdapter(typeToken, GsonDeserializer())
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl(redditUrl)
                .client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        redditApi = retrofit.create(RedditApi::class.java)
    }

}