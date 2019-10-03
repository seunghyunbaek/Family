package com.hyun.familyapplication.view.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hyun.familyapplication.R
import com.hyun.familyapplication.model.RecordImage
import kotlinx.android.synthetic.main.item_picture.view.*

class PictureAdapter(internal val context: Context) :
    RecyclerView.Adapter<PictureAdapter.PictureViewHolder>() {
    var lstImages: ArrayList<RecordImage>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder =
        PictureViewHolder(parent)

    override fun getItemCount(): Int = if (lstImages == null) 0 else lstImages!!.size

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        with(holder) {
            Glide.with(context).load(lstImages?.get(position)?.uri).into(this.imgPicture)

            this.itemView.setOnClickListener {
                Toast.makeText(context, lstImages?.get(position)?.uri, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun dataChange(lstImages: ArrayList<RecordImage>) {
        this.lstImages = lstImages
        notifyDataSetChanged()
    }

    inner class PictureViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_picture, parent, false)
    ) {
        val imgPicture = itemView.item_image_picture
    }
}