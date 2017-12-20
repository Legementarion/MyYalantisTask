package com.lego.myyalantistask.repository.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [(News::class)], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

}