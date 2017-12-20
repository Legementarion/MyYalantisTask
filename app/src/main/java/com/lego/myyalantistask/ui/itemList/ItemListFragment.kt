package com.lego.myyalantistask.ui.itemList

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.lego.myyalantistask.MainApp
import com.lego.myyalantistask.R
import com.lego.myyalantistask.repository.db.News
import kotlinx.android.synthetic.main.fragment_itemlist.*
import javax.inject.Inject

class ItemListFragment : MvpAppCompatFragment(), ItemListView, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var listAdapter: ItemListAdapter

    @InjectPresenter
    lateinit var itemListPresenter: ItemListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainApp.graph.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(recyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            adapter = listAdapter
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_itemlist, container, false)
    }

    override fun showErrorMessage() {
        Toast.makeText(context, R.string.error, Toast.LENGTH_LONG).show()
    }

    override fun showProgressbar(visible: Boolean) {
        when (visible) {
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }

    override fun showResultList(list: List<News>) {
        listAdapter.addAll(list)
    }

    override fun onRefresh() {
        itemListPresenter.refreshData()
    }

}