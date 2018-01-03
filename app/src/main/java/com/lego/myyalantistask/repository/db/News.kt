package com.lego.myyalantistask.repository.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "top")
data class News(
        @PrimaryKey
        @ColumnInfo(name = "id")
        var id: String = "",
        @ColumnInfo(name = "author")
        var author: String = "",
        @ColumnInfo(name = "title")
        var title: String = "",
        @ColumnInfo(name = "subreddit")
        var subreddit: String = "",
        @ColumnInfo(name = "permalink")
        var url: String = "",
        @ColumnInfo(name = "textHtml")
        @SerializedName("selftext_html")
        var textHtml: String? = "",
        @ColumnInfo(name = "created")
        @SerializedName("created_utc")
        var created: Long = 0,
        @ColumnInfo(name = "commentsCount")
        @SerializedName("num_comments")
        var commentsCount: Int = 0,
        @ColumnInfo(name = "score")
        var score: Int = 0,
        @ColumnInfo(name = "thumbnail")
        var thumbnail: String = ""
)

data class Result(val data: List<News>)