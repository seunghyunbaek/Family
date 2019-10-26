package com.hyun.familyapplication.view.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.InvitedContract
import com.hyun.familyapplication.model.Invite
import kotlinx.android.synthetic.main.item_invited.view.*

class InvitedAdapter : RecyclerView.Adapter<InvitedAdapter.InvitedViewHolder>() {

    private var list: ArrayList<Invite>? = null
    private var listener:InvitedContract.Listener? = null
    private var context:Context? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvitedViewHolder {
        return InvitedViewHolder(parent)
    }

    override fun getItemCount(): Int {
        if (list == null)
            return 0
        else
            return list!!.size
    }

    override fun onBindViewHolder(holder: InvitedViewHolder, position: Int) {
        list?.get(position).let{
            with(holder) {
                val inviter = it?.inviter
                name.setText(inviter?.replace("_", "."))
                btn.setOnClickListener {
//                    println("수락하기 누름")
                    listener?.onPositive(context!!, list?.get(position)!!)
                }
            }
        }
    }

    fun setListener(listener:InvitedContract.Listener) {
        this.listener = listener
    }
    fun setData(list:ArrayList<Invite>) {
        this.list = list
    }
    fun setContext(context: Context) {
        this.context = context
    }

    inner class InvitedViewHolder(parent:ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_invited, parent, false)
    ) {
        val btn = itemView.item_btn_invite_invited
        val name = itemView.item_text_name_invited
    }
}