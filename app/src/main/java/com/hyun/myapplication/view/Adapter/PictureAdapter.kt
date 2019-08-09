package com.hyun.myapplication.view.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyun.myapplication.R
import com.hyun.myapplication.model.MainData
import com.hyun.myapplication.model.User
import kotlinx.android.synthetic.main.item_picture.view.*

class PictureAdapter : RecyclerView.Adapter<PictureAdapter.PictureViewHolder>() {

    var items: MutableList<MainData> = mutableListOf(
        MainData("김김일", "Content1"),
        MainData("김김이", "Content2"), MainData("김김삼", "Content3"),
        MainData("김김사", "Content4"), MainData("김김오", "Content5"),
        MainData("김김육", "Content6"), MainData("김김칠", "Content7"),
        MainData("김김팔", "Content8"), MainData("김김구", "Content9"),
        MainData("김김십", "Content10")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder = PictureViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
//        items[position].let {item ->
//            with(holder) {
//                imgPicture.setImageResource(R.drawable.chicken)
//            }/**/
//        }
        with(holder) {
            imgPicture.setImageResource(R.drawable.chicken)
        }
//        holder.imgPicture.setImageResource(R.drawable.chicken)
    }

    inner class PictureViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_picture, parent, false)) {
        val imgPicture = itemView.img_picture
    }
}