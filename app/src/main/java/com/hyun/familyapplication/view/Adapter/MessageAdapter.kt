package com.hyun.familyapplication.view.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyun.familyapplication.R
import com.hyun.familyapplication.model.Message
import kotlinx.android.synthetic.main.item_message.view.*

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    var list: ArrayList<Message>? = null

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
        list?.get(position).let {
            with(holder) {
                sender.text = it?.sender?.replace("_", ".")
                receiver.text = it?.receiver?.replace("_", ".")
                content.text = it?.content
            }
        }
    }

    fun setData(list:ArrayList<Message>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
    ) {
        val sender = itemView.item_text_sender2
        val receiver = itemView.item_text_receiver2
        val content = itemView.tv_main_message
    }
}