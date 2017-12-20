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
import javax.inject.Named
import javax.inject.Singleton

@Module
class BaseModule(private var context: Context) {

    @Provides
    @Named("serverUrl")
    fun provideUrl(): String {
        return "https://www.reddit.com/"
    }

    @Provides
    @Singleton
    fun providesContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideRequestController(@Named("serverUrl") serverUrl: String) = RequestController(serverUrl).redditApi

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
    fun provideItemListAdapter(@Named("serverUrl") serverUrl: String) = ItemListAdapter(serverUrl, context)

}