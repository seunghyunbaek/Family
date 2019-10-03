package com.hyun.familyapplication.view.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyun.familyapplication.R
import com.hyun.familyapplication.model.User
import kotlinx.android.synthetic.main.item_family.view.*

class FamilyAdapter : RecyclerView.Adapter<FamilyAdapter.FamilyViewHolder>() {

    private var items: MutableList<User>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyViewHolder =
        FamilyViewHolder(parent)

    override fun getItemCount(): Int = if (items == null) 0 else items!!.size

    override fun onBindViewHolder(holder: FamilyViewHolder, position: Int) {
        items?.get(position).let { item ->
            with(holder) {
                tvName.setText(item?.name)
                imgOut.visibility = View.INVISIBLE
            }
        }
    }

    fun setData(lst:ArrayList<User>) {
        items = lst
    }

    inner class FamilyViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_family, parent, false)
    ) {
        val imgProfile = itemView.item_image_profile_family
        val tvName = itemView.item_text_name_family
        val imgOut = itemView.item_image_exit_family
    }
}