package com.lego.myyalantistask.ui.itemList

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.lego.myyalantistask.MainApp
import com.lego.myyalantistask.repository.TopNewsRepositoryController
import com.lego.myyalantistask.ui.itemList.rv.OnBottomReachedListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class ItemListPresenter : MvpPresenter<ItemListView>(), OnBottomReachedListener {

    private var currentRVCount = 0
    private val count = 10
    private val limit = 50

    @Inject
    lateinit var repositoryController: TopNewsRepositoryController

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        MainApp.graph.inject(this)
        currentRVCount = 0
        initData()
    }

    fun initData() {
        viewState.setBottomListener(this)
        repositoryController
                .getNews(count, limit)
                .doOnSubscribe {
                    viewState.showProgressbar(true)
                }
                .doOnTerminate {
                    viewState.showProgressbar(false)
                    viewState.hideRefreshing()
                }
                .subscribe({
                    currentRVCount += count
                    viewState.showResultList(it)
                }, {
                    viewState.showErrorMessage()
                })
    }

    override fun onBottomReached(position: Int) {
        loadData()
    }

    private fun loadData() {
        if (currentRVCount < limit) {
            repositoryController.getNewsFromDb(currentRVCount, count)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { viewState.showRVLoading() }
                    .doOnTerminate { viewState.hideRVLoading() }
                    .subscribe({
                        currentRVCount += count
                        viewState.addToResultList(it)
                    }, {
                        viewState.showErrorMessage()
                    })
        }
    }
}