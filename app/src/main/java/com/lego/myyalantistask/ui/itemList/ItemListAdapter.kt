package com.lego.myyalantistask.ui.itemList

import android.content.Context
import android.net.Uri
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

class ItemListAdapter(@Named("serverUrl") private val redditUrl: String, private val context: Context) : RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {

    private var items: List<News> = Collections.emptyList()

    init {
        MainApp.graph.inject(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.card_itemlist, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        if (item.thumbnail != "") {
            Glide.with(context)
                    .load(item.thumbnail)
                    .apply(centerCropTransform()
                    .error(R.drawable.reddit_logo))
                    .into(holder.thumbnail)
        }

        with(holder.container) {
            holder.title.text = item.title
            holder.created.text = DateUtils.getRelativeTimeSpanString(item.created * 1000L, Date().time, 1L)
            holder.subreddit.text = item.subreddit
            holder.text.text = item.text
            holder.author.text = item.author
            holder.score.text = item.score.toString()
            holder.comment.text = item.commentsCount.toString()

            holder.container.setOnClickListener({
                val builder = CustomTabsIntent.Builder()
                builder.setToolbarColor(ContextCompat.getColor(context, R.color.gray))
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(context, Uri.parse(redditUrl + item.url))
            })
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addAll(list: List<News>) {
        if (!list.isEmpty()) {
            items = list
            notifyDataSetChanged()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container = view.cardContainer
        val title = view.title
        val created = view.created
        val subreddit = view.subreddit
        val thumbnail = view.thumbnail
        val text = view.text
        val author = view.authorField
        val score = view.scoreField
        val comment = view.commentsField
    }
}