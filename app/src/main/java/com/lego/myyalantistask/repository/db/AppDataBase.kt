package com.lego.myyalantistask.repository.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(News::class), version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

}