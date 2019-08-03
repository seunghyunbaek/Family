package com.hyun.myapplication.view.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyun.myapplication.R
import com.hyun.myapplication.model.MainData
import com.hyun.myapplication.model.User
import kotlinx.android.synthetic.main.item_family.view.*

class FamilyAdapter : RecyclerView.Adapter<FamilyAdapter.FamilyViewHolder>() {

    var items: MutableList<User> = mutableListOf(
        User("김김일", "Content1"),
        User("김김이", "Content2"), User("김김삼", "Content3"),
        User("김김사", "Content4"), User("김김오", "Content5"),
        User("김김육", "Content6"), User("김김칠", "Content7"),
        User("김김팔", "Content8"), User("김김구", "Content9"),
        User("김김십", "Content10")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyViewHolder = FamilyViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FamilyViewHolder, position: Int) {
        items[position].let { item ->
            with(holder)  {
                tvName.text = item.email
            }
        }
    }

    inner class FamilyViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_family, parent, false)){
        val imgProfile = itemView.img_profile_family
        val tvName = itemView.tv_name_family
        val imgOut = itemView.img_out_profile
    }
}