package com.lego.myyalantistask.repository.db

import android.arch.persistence.room.*
import io.reactivex.Single

@Dao
interface NewsDao {

    @Query("SELECT * FROM top")
    fun getAllNews(): Single<List<News>>

    @Query("SELECT * FROM top LIMIT :arg0 OFFSET :arg1")
    fun getNews(limit: Int, offset: Int): Single<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<News>)

    @Query("DELETE FROM top")
    fun deleteAll()

}