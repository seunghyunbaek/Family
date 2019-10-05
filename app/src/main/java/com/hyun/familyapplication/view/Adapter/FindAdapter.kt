package com.hyun.familyapplication.view.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.model.User
import kotlinx.android.synthetic.main.item_family.view.*

class FindAdapter(context: Context) : RecyclerView.Adapter<FindAdapter.FindViewHolder>() {

    private var list: ArrayList<User>? = null
    private var dbHelper: DBHelper? = null
    private var self: User? = null

    init {
        dbHelper = DBHelper(context)
        self = dbHelper!!.getUser()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FindAdapter.FindViewHolder = FindViewHolder(parent)

    override fun getItemCount(): Int = if (list == null) 0 else list!!.size

    override fun onBindViewHolder(holder: FindViewHolder, position: Int) {
        list?.get(position).let {
            with(holder) {
                //                tvName.setText(list?.get(position)?.name)
                tvName.setText(it?.name)
                if (self?.room == it?.room) {
                    imgOut.visibility = View.GONE
                } else {
                    imgOut.visibility = View.VISIBLE
                }
            }
        }
    }

    fun setData(list: ArrayList<User>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class FindViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_family, parent, false)
    ) {
        val imgProfile = itemView.item_image_profile_family
        val tvName = itemView.item_text_name_family
        val imgOut = itemView.item_image_exit_family
    }
}