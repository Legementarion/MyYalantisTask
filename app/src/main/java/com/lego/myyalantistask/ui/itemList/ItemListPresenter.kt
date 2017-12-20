package com.lego.myyalantistask.ui.itemList

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.lego.myyalantistask.MainApp
import com.lego.myyalantistask.repository.TopNewsRepositoryController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class ItemListPresenter : MvpPresenter<ItemListView>() {

    private val count = 50

    @Inject
    lateinit var repositoryController: TopNewsRepositoryController

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        MainApp.graph.inject(this)

        refreshData()
    }

    fun refreshData() {

        repositoryController
                .getNews(count)
                .doOnSubscribe { viewState.showProgressbar(true) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate { viewState.showProgressbar(false) }
                .subscribe({
                    viewState.showResultList(it)
                }, {
                    viewState.showErrorMessage()
                })
    }
}