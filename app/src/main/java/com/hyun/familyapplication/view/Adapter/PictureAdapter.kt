package com.hyun.familyapplication.view.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hyun.familyapplication.R
import com.hyun.familyapplication.model.RecordImage
import com.hyun.familyapplication.view.Activity.ImageActivity
import com.hyun.familyapplication.view.Activity.MyHomeActivity
import kotlinx.android.synthetic.main.item_picture.view.*

class PictureAdapter(internal val activity: MyHomeActivity, internal val context: Context) :
    RecyclerView.Adapter<PictureAdapter.PictureViewHolder>() {
    var lstImages: ArrayList<RecordImage>? = null

    var previousPosition: Int = Int.MIN_VALUE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder =
        PictureViewHolder(parent)

    override fun getItemCount(): Int = if (lstImages == null) 0 else lstImages!!.size

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        with(holder) {

//            println("position:$position // previousPosition:$previousPosition")
            if (position > previousPosition ) {
                fade_out(this.itemView)
                previousPosition = position
            }

            Glide.with(context).load(lstImages?.get(position)?.uri).into(this.imgPicture)


            this.itemView.setOnClickListener {
                //                Toast.makeText(context, lstImages?.get(position)?.uri, Toast.LENGTH_SHORT).show()
                val intent = Intent(context, ImageActivity::class.java)
                intent.putExtra("uri", lstImages?.get(position)?.uri)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity,
                    itemView,
                    ViewCompat.getTransitionName(itemView)!!
                )
                context!!.startActivity(intent, options.toBundle())
            }
        }
    }

    fun dataChange(lstImages: ArrayList<RecordImage>) {
        this.lstImages = lstImages
        notifyDataSetChanged()
    }

    private fun fade_out(view: View) {
        //AnimationSet set = new AnimationSet(true);
        val a: AlphaAnimation = AlphaAnimation(0.0f, 1.0f)
        a.setFillAfter(true); // 에니메이션이 끝난 뒤 상태를 유지하는 설정, 설정하지 않으면 duration 이후 원래 상태로 되돌아감
        a.setDuration(2000);
        view.startAnimation(a);
    }

    inner class PictureViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_picture, parent, false)
    ) {
        val imgPicture = itemView.item_image_picture
    }
}