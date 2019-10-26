package com.hyun.familyapplication.view.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.model.User
import kotlinx.android.synthetic.main.item_family.view.*
import kotlinx.android.synthetic.main.item_find.view.*

class FindAdapter(context: Context) : RecyclerView.Adapter<FindAdapter.FindViewHolder>() {

    private var list: ArrayList<User>? = null
    private var dbHelper: DBHelper? = null
    private var self: User? = null
    private var dialog:AlertDialog.Builder? = null

    private var guest:String? = null
    private var inviter:String? = null
    private var room:Int? = null

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
//                    imgOut.visibility = View.GONE
                    btnInvite.visibility = View.GONE
                } else {
//                    imgOut.visibility = View.VISIBLE
                    btnInvite.visibility = View.VISIBLE
                }
                this.btnInvite.setOnClickListener {
//                   println("초대하기")
                    dialog?.show()
                    guest = list?.get(position)?.email
                    room = self?.room
                    inviter = self?.email
                }
            }
        }
    }

    fun getGuest() = guest
    fun getRoom() = room
    fun getInviter() = inviter

    fun setData(list: ArrayList<User>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun setDialog(dialog:AlertDialog.Builder) {
        this.dialog = dialog
    }

    inner class FindViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_find, parent, false)
    ) {
        val imgProfile = itemView.item_image_profile_find
        val tvName = itemView.item_text_name_find
//        val imgOut = itemView.item_image_exit_find
        val btnInvite = itemView.item_btn_invite_find
    }
}