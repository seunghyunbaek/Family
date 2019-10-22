package com.hyun.familyapplication.view.Adapter

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog.Builder
import androidx.recyclerview.widget.RecyclerView
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.FamilyContract
import com.hyun.familyapplication.model.User
import kotlinx.android.synthetic.main.item_family.view.*

class FamilyAdapter(context: Context) : RecyclerView.Adapter<FamilyAdapter.FamilyViewHolder>() {

    private var items: MutableList<User>? = null
    private var email: String? = ""
    private var dialog: androidx.appcompat.app.AlertDialog.Builder? = null
    private var listener: FamilyContract.Listener? = null
    private var ctx:Context = context
    private var dialogListener = object : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface?, which: Int) {
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    if(items!!.size == 1) {
                        listener?.onDeleteRoom(ctx)
                    } else {
                        listener?.onExit()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyViewHolder =
        FamilyViewHolder(parent)

    override fun getItemCount(): Int = if (items == null) 0 else items!!.size

    override fun onBindViewHolder(holder: FamilyViewHolder, position: Int) {
        items?.get(position).let { item ->
            with(holder) {
                tvName.setText(item?.name)
                if (email?.equals(item?.email)!!) {
                    imgOut.visibility = View.VISIBLE
                    imgOut.setOnClickListener {
                        if (dialog != null)
                            dialog!!.show()
                    }
                } else {
                    imgOut.visibility = View.INVISIBLE
                }
            }
        }
    }

    fun setListener(listener: FamilyContract.Listener) {
        this.listener = listener
    }

    fun setData(lst: ArrayList<User>) {
        items = lst
    }

    fun setEmail(e: String) {
        this.email = e
    }

    fun setDialog(context: Context) {
        dialog = Builder(context)
        if (dialog != null) {
            dialog!!.setMessage("방을 나가시겠습니까?")
            dialog!!.setIcon(R.mipmap.ic_launcher)
            dialog!!.setPositiveButton("예", dialogListener)
            dialog!!.setNegativeButton("아니오", dialogListener)
        }
    }

    inner class FamilyViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_family, parent, false)
    ) {
        val imgProfile = itemView.item_image_profile_family
        val tvName = itemView.item_text_name_family
        val imgOut = itemView.item_image_exit_family
    }
}