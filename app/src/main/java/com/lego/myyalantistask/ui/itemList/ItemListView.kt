package com.lego.myyalantistask.ui.itemList

import com.arellomobile.mvp.MvpView
import com.lego.myyalantistask.repository.db.News
import com.lego.myyalantistask.ui.itemList.rv.OnBottomReachedListener

interface ItemListView : MvpView {

    fun showErrorMessage()

    fun showProgressbar(visible: Boolean)

    fun showRVLoading()

    fun hideRefreshing()

    fun hideRVLoading()

    fun showResultList(list: List<News>)

    fun addToResultList(list: List<News>)

    fun setBottomListener(onBottomReachedListener: OnBottomReachedListener)

}