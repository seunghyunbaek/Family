package com.hyun.familyapplication.view.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyun.familyapplication.R
import com.hyun.familyapplication.model.MainData
import com.hyun.familyapplication.model.News
import com.hyun.familyapplication.view.Activity.NewsDetailActivity
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(context: Context, newsList:ArrayList<News>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    var context:Context
    var items: ArrayList<News>
    init {
        this.context = context
        this.items = newsList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        items[position].let { item ->
            with(holder) {
                tvTitle.text = item.title
                tvTime.text = item.subtitle
//                if (position % 2 == 0)
//                    imgNews.visibility = View.VISIBLE
                this.itemView.setOnClickListener {
                    Intent(context, NewsDetailActivity::class.java).let {
                        it.putExtra("title", item.title)
                        it.putExtra("subtitle", item.subtitle)
                        it.putExtra("content", item.content)
                        context.startActivity(it)
                    }
                }
            }
        }
    }

    inner class NewsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
    ) {
        val tvTitle = itemView.tvTitleNews
        val tvTime = itemView.tvTimeNews
        val imgNews = itemView.imgNews
    }
}