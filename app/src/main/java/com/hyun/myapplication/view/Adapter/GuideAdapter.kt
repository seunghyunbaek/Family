package com.hyun.myapplication.view.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyun.myapplication.R
import com.hyun.myapplication.model.MainData
import kotlinx.android.synthetic.main.item_guide.view.*

class GuideAdapter : RecyclerView.Adapter<GuideAdapter.GuideViewHolder>() {

    var items: MutableList<MainData> = mutableListOf(
        MainData("Guide1", "Content1"),
        MainData("Guide2", "Content2"), MainData("Guide3", "Content3"),
        MainData("Guide4", "Content4"), MainData("Guide5", "Content5"),
        MainData("Guide6", "Content6"), MainData("Guide7", "Content7"),
        MainData("Guide8", "Content8"), MainData("Guide9", "Content9"),
        MainData("Guide10", "Content10")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuideViewHolder =
        GuideViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GuideViewHolder, position: Int) {
        items[position].let { item ->
            with(holder) {
                tvTitle.text = item.title
                tvContent.text = item.content
            }
        }
    }

    inner class GuideViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_guide, parent, false)
    ) {
        val tvTitle = itemView.item_text_title_guide
        val tvContent = itemView.item_text_content_guide
    }
}