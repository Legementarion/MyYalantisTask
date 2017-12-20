package com.lego.myyalantistask.ui.activity

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.lego.myyalantistask.ui.itemList.ItemListFragment

@InjectViewState
class ActivityPresenter : MvpPresenter<ActivityView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.showFragment(ItemListFragment())
    }

}