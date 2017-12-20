package com.lego.myyalantistask.core

import com.lego.myyalantistask.repository.db.Result
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApi {

    @GET("/top.json")
    fun getTopNews(@Query("limit") count: Int): Observable<Result>

}