package com.hyun.familyapplication.view.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyun.familyapplication.R
import kotlinx.android.synthetic.main.item_message.view.*

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    var list: ArrayList<Int>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun getItemCount(): Int {
        if (list == null)
            return 0
        else
            return list!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
    ) {
        val send = itemView.item_text_send_message
        val receive = itemView.item_text_receive_message
        val message = itemView.tv_main_message
        val viewpager = itemView.viewpager_item_message
    }
}