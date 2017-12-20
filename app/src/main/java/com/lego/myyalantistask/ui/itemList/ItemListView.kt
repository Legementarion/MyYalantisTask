package com.lego.myyalantistask.ui.itemList

import com.arellomobile.mvp.MvpView
import com.lego.myyalantistask.repository.db.News

interface ItemListView : MvpView {

    fun showErrorMessage()

    fun showProgressbar(visible: Boolean)

    fun showResultList(list: List<News>)

}