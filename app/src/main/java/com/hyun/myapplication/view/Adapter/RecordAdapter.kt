package com.hyun.myapplication.view.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyun.myapplication.R
import com.hyun.myapplication.model.MainData
import kotlinx.android.synthetic.main.item_main.view.*

class RecordAdapter : RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {

    var items: MutableList<MainData> = mutableListOf(MainData("Title1", "Content1"),
        MainData("Title2", "Content2"), MainData("Title3", "Content3"),
        MainData("Title4", "Content4"), MainData("Title5", "Content5"),
        MainData("Title6", "Content6"), MainData("Title7", "Content7"),
        MainData("Title8", "Content8"), MainData("Title9", "Content9"),
        MainData("Title10", "Content10"), MainData("Title11", "Content11"),
        MainData("Title12", "Content12"), MainData("Title13", "Content13"))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecordViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecordAdapter.RecordViewHolder, position: Int) {
        items[position].let { item ->
            with(holder) {
                tvTitle.text = item.title
                tvContent.text = item.content
            }
        }
    }

    inner class RecordViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)) {
        val tvTitle = itemView.tv_main_title
        val tvContent = itemView.tv_main_content
    }

}