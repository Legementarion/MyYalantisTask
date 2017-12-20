package com.lego.myyalantistask.repository

import com.lego.myyalantistask.core.RedditApi
import com.lego.myyalantistask.repository.db.News
import com.lego.myyalantistask.repository.db.NewsDao
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class TopNewsRepositoryController(val topNewsApi: RedditApi, val newsDao: NewsDao) {

    fun getNews(count: Int): Observable<List<News>> {
        return getNewsFromApi(count)
//        Observable.concatArray(
//                getNewsFromDb(),

//        )
    }

    private fun getNewsFromDb(): Observable<List<News>> {
        return newsDao.getNews().filter { it.isNotEmpty() }
                .toObservable()
                .doOnNext {
                }
    }

    private fun getNewsFromApi(count: Int): Observable<List<News>> {
        return topNewsApi.getTopNews(count)
                .map { it -> it.data }
//                .doOnNext {
//                    storeNewsInDb(it)
//                }
    }

    private fun storeNewsInDb(news: List<News>) {
        Observable.fromCallable { newsDao.insertAll(news) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {

                }
    }

}