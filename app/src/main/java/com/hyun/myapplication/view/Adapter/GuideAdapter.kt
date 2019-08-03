package com.hyun.myapplication.view.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyun.myapplication.R
import com.hyun.myapplication.model.User
import kotlinx.android.synthetic.main.item_guide.view.*

class GuideAdapter : RecyclerView.Adapter<GuideAdapter.GuideViewHolder>() {

    var items: MutableList<User> = mutableListOf(
        User("Guide1", "Content1"),
        User("Guide2", "Content2"), User("Guide3", "Content3"),
        User("Guide4", "Content4"), User("Guide5", "Content5"),
        User("Guide6", "Content6"), User("Guide7", "Content7"),
        User("Guide8", "Content8"), User("Guide9", "Content9"),
        User("Guide10", "Content10")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuideViewHolder =
        GuideViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GuideViewHolder, position: Int) {
        items[position].let { item ->
            with(holder) {
                tvTitle.text = item.email
                tvContent.text = item.passwd
            }
        }
    }

    inner class GuideViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_guide, parent, false)
    ) {
        val tvTitle = itemView.tvTitleGuide
        val tvContent = itemView.tvContentGuide
    }
}