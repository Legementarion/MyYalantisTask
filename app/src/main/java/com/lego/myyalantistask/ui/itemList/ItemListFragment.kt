package com.lego.myyalantistask.ui.itemList

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.lego.myyalantistask.MainApp
import com.lego.myyalantistask.R
import com.lego.myyalantistask.repository.db.News
import com.lego.myyalantistask.ui.itemList.rv.ItemListAdapter
import com.lego.myyalantistask.ui.itemList.rv.OnBottomReachedListener
import kotlinx.android.synthetic.main.fragment_itemlist.*
import javax.inject.Inject
import android.support.v7.widget.RecyclerView

class ItemListFragment : MvpAppCompatFragment(), ItemListView, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var listAdapter: ItemListAdapter

    @InjectPresenter
    lateinit var itemListPresenter: ItemListPresenter
    var loading = false
    lateinit var onBottomReachedListener: OnBottomReachedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainApp.graph.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var pastVisibleItems: Int
        var visibleItemCount: Int
        var totalItemCount: Int
        val manager = LinearLayoutManager(context)
        refreshSwipe.setOnRefreshListener(this)
        with(recyclerView) {
            layoutManager = manager
            adapter = listAdapter

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        visibleItemCount = manager.childCount
                        totalItemCount = manager.itemCount
                        pastVisibleItems = manager.findFirstVisibleItemPosition()

                        if (!loading) {
                            if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                                loading = true
                                onBottomReachedListener.onBottomReached(listAdapter.itemCount)
                            }
                        }
                    }
                }
            })
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

    override fun showRVLoading() {
        listAdapter.showUploading()
        recyclerView.post({
            listAdapter.notifyDataSetChanged()
        })
    }

    override fun hideRVLoading() {
        loading = false
        listAdapter.hideUploading()
        recyclerView.post({
            listAdapter.notifyDataSetChanged()
        })
    }

    override fun showResultList(list: List<News>) {
        listAdapter.addAll(list)
    }

    override fun addToResultList(list: List<News>) {
        listAdapter.uploadNew(list)
    }

    override fun onRefresh() {
        itemListPresenter.initData()
    }

    override fun hideRefreshing() {
        refreshSwipe.isRefreshing = false
    }

    override fun setBottomListener(onBottomReachedListener: OnBottomReachedListener) {
        this.onBottomReachedListener = onBottomReachedListener
    }

}