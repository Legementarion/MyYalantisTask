package com.lego.myyalantistask.repository

import com.lego.myyalantistask.core.RedditApi
import com.lego.myyalantistask.repository.db.News
import com.lego.myyalantistask.repository.db.NewsDao
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class TopNewsRepositoryController(private val topNewsApi: RedditApi, private val newsDao: NewsDao) {

    fun getNews(count: Int, limit: Int): Observable<List<News>> {
        return getNewsFromApi(limit)
                .map { it.take(count).sortedByDescending { it.score } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getNewsFromDb(offset: Int, limit: Int): Observable<List<News>> {
        return newsDao.getNews(limit, offset)
                .map { it.take(limit).sortedByDescending { it.score } }
                .toObservable().delay(2500, TimeUnit.MILLISECONDS)  //delay for pagination
    }

    private fun clearNews() {
        newsDao.deleteAll()
    }

    private fun getNewsFromApi(limit: Int): Observable<List<News>> {
        return topNewsApi.getTopNews(limit)
                .map { it -> it.data }
                .doOnNext {
                    storeNewsInDb(it)
                }
    }

    private fun storeNewsInDb(news: List<News>) {
        Observable.fromCallable {
            clearNews()
            newsDao.insertAll(news)
        }
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

}