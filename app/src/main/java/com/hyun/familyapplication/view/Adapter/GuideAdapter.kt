package com.hyun.familyapplication.view.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyun.familyapplication.R
import com.hyun.familyapplication.model.Guide
import com.hyun.familyapplication.model.MainData
import com.hyun.familyapplication.view.Activity.GuideDetailActivity
import kotlinx.android.synthetic.main.item_guide.view.*

class GuideAdapter(context: Context, items:Array<Guide>) : RecyclerView.Adapter<GuideAdapter.GuideViewHolder>() {

    var items:Array<Guide>
    var context:Context
    init {
        this.context = context
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuideViewHolder =
        GuideViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GuideViewHolder, position: Int) {
        items[position].let { item ->
            with(holder) {
                tvTitle.text = item.title
                tvSubtitle.text = item.subtitle

                this.itemView.setOnClickListener {
                    Intent(context, GuideDetailActivity::class.java).let{
                        it.putExtra("title", item.title)
                        it.putExtra("subtitle", item.subtitle)
                        it.putExtra("description", item.description)
                        it.putExtra("images", item.image)
                        context.startActivity(it)
                    }
                }
            }
        }
    }

    inner class GuideViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_guide, parent, false)
    ) {
        val tvTitle = itemView.item_text_title_guide
        val tvSubtitle = itemView.item_text_subtitle_guide
    }
}