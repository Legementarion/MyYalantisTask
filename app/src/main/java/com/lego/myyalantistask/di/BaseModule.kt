package com.lego.myyalantistask.di

import android.arch.persistence.room.Room
import android.content.Context
import com.lego.myyalantistask.core.RedditApi
import com.lego.myyalantistask.core.RequestController
import com.lego.myyalantistask.repository.TopNewsRepositoryController
import com.lego.myyalantistask.repository.db.AppDataBase
import com.lego.myyalantistask.ui.itemList.ItemListAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BaseModule(var context: Context) {

    @Provides
    @Singleton
    fun providesContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideRequestController() = RequestController().redditApi

    @Provides
    @Singleton
    fun provideDataBase(): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, "newsBase").build()
    }

    @Provides
    @Singleton
    fun provideTopNewsRepository(appDataBase: AppDataBase, redditApi: RedditApi) = TopNewsRepositoryController(redditApi, appDataBase.newsDao())

    @Provides
    @Singleton
    fun provideItemListAdapter() = ItemListAdapter(context)

}