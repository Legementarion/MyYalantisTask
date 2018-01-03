package com.lego.myyalantistask.ui.itemList.rv

import android.content.Context
import android.net.Uri
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lego.myyalantistask.R
import com.lego.myyalantistask.repository.db.News
import kotlinx.android.synthetic.main.card_itemlist.view.*
import java.util.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.centerCropTransform
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import com.lego.myyalantistask.MainApp
import javax.inject.Named
import android.text.format.DateUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.card_loading.view.*

class ItemListAdapter(@Named("serverUrl") private val redditUrl: String, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemType = 0
    private val loadingType = 1
    private var items: MutableList<News?> = mutableListOf()

    init {
        MainApp.graph.inject(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == itemType) {
            NewsViewHolder(layoutInflater.inflate(R.layout.card_itemlist, parent, false))
        } else {
            LoadingViewHolder(layoutInflater.inflate(R.layout.card_loading, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NewsViewHolder) {
            val item = items[position]

            if (item?.thumbnail?.contains("http")!!) {
                Glide.with(context)
                        .load(item.thumbnail)
                        .apply(centerCropTransform()
                                .error(R.drawable.reddit_logo))
                        .into(holder.thumbnail)
            } else {
                Glide.with(context)
                        .load(R.drawable.reddit_logo)
                        .apply(centerCropTransform())
                        .into(holder.thumbnail)
            }

            with(holder) {
                title.text = item.title
                created.text = DateUtils.getRelativeTimeSpanString(item.created * 1000L, Date().time, 1L)
                subreddit.text = item.subreddit
                author.text = item.author
                score.text = item.score.toString()
                comment.text = item.commentsCount.toString()

                container.setOnClickListener({
                    val builder = CustomTabsIntent.Builder()
                    builder.setToolbarColor(ContextCompat.getColor(context, R.color.gray))
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(context, Uri.parse(redditUrl + item.url))
                })
            }

        } else if (holder is LoadingViewHolder) {
            holder.progressBar.isIndeterminate = true
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] == null) {
            loadingType
        } else {
            itemType
        }
    }

    fun addAll(list: List<News>) {
        if (!list.isEmpty()) {
            items.clear()
            items.addAll(list.toMutableList())
            notifyDataSetChanged()
        }
    }

    fun showUploading() {
        items.add(null)
    }

    fun hideUploading() {
        items.remove(null)
    }

    fun uploadNew(list: List<News>) {
        if (!list.isEmpty()) {
            items.addAll(list.toMutableList())
        }
    }

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container: ConstraintLayout = view.cardContainer
        val title: TextView = view.title
        val created: TextView = view.created
        val subreddit: TextView = view.subreddit
        val thumbnail: ImageView = view.thumbnail
        val author: TextView = view.authorField
        val score: TextView = view.scoreField
        val comment: TextView = view.commentsField
    }

    class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val progressBar: ProgressBar = view.paginationProgressBar
    }
}