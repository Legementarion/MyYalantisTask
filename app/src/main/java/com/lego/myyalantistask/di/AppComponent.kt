package com.lego.myyalantistask.di

import com.lego.myyalantistask.ui.itemList.ItemListAdapter
import com.lego.myyalantistask.ui.itemList.ItemListFragment
import com.lego.myyalantistask.ui.itemList.ItemListPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(BaseModule::class)])
interface AppComponent {

    fun inject(itemListPresenter: ItemListPresenter)

    fun inject(itemListAdapter: ItemListAdapter)

    fun inject(itemListFragment: ItemListFragment)

}