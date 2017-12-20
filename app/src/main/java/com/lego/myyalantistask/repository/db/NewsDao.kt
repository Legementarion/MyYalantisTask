package com.lego.myyalantistask.repository.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface NewsDao {

    @Query("SELECT * FROM top")
    fun getNews(): Single<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: News)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<News>)

}