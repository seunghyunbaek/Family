package com.hyun.myapplication.view.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyun.myapplication.R
import com.hyun.myapplication.model.User
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    var items: MutableList<User> = mutableListOf(
        User("김김일", "Content1"),
        User("김김이", "Content2"), User("김김삼", "Content3"),
        User("김김사", "Content4"), User("김김오", "Content5"),
        User("김김육", "Content6"), User("김김칠", "Content7"),
        User("김김팔", "Content8"), User("김김구", "Content9"),
        User("김김십", "Content10")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        items[position].let { item ->
            with(holder) {
                tvTitle.text = item.passwd
                tvTime.text = "19년 8월 4일"
                if (position % 2 == 0)
                    imgNews.visibility = View.VISIBLE
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