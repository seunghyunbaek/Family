package com.hyun.familyapplication.view.Adapter

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.TransferContract
import com.hyun.familyapplication.model.User
import kotlinx.android.synthetic.main.item_transfer.view.*
import androidx.appcompat.app.AlertDialog.Builder

class TransferAdapter : RecyclerView.Adapter<TransferAdapter.TransferViewHolder>() {

    private var items: MutableList<User>? = null
    private var email: String? = ""
    private var dialog: androidx.appcompat.app.AlertDialog.Builder? = null
    private var listener: TransferContract.Listener? = null
    private var context:Context? = null
    private var dialogListener = object : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface?, which: Int) {
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> listener?.onSelected(context!!, transItem!!)
                else -> transItem = null
            }
        }
    }
    private var transItem:User ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransferViewHolder =
        TransferViewHolder(parent)

    override fun getItemCount(): Int = if (items == null) 0 else items!!.size

    override fun onBindViewHolder(holder: TransferViewHolder, position: Int) {
        items?.get(position).let { item ->
            with(holder) {
                tvName.setText(item?.name)
//                if (email?.equals(item?.email)!!) {
//                    imgOut.visibility = View.VISIBLE
//                    imgOut.setOnClickListener {
//                        if (dialog != null)
//                            dialog!!.show()
//                    }
//                } else {
//                    imgOut.visibility = View.INVISIBLE
//                }

                this.itemView.setOnClickListener {
                    println("아이템 클릭했음 : $position")
                    dialog?.setMessage(item?.name + "님 에게 양도하시겠습니까?")
                    val dialogResult = dialog?.show()
                    transItem = item
                    println("----------------------------------")
                    println("----------------------------------")
                }
            }
        }
    }

    fun setListener(listener: TransferContract.Listener) {
        this.listener = listener
    }

    fun setData(lst: ArrayList<User>) {
        items = lst
    }

    fun setEmail(e: String) {
        this.email = e
    }

    fun setContext(context: Context) {
        this.context = context
    }

    fun setDialog(context: Context) {
        dialog = Builder(context)
        if (dialog != null) {
            dialog!!.setMessage("방을 양도하시겠습니까?")
            dialog!!.setIcon(R.mipmap.ic_launcher)
            dialog!!.setPositiveButton("예", dialogListener)
            dialog!!.setNegativeButton("아니오", dialogListener)
        }
    }

    inner class TransferViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_transfer, parent, false)
    ) {
        val imgProfile = itemView.item_image_profile_transfer
        val tvName = itemView.item_text_name_transfer
        val imgOut = itemView.item_image_exit_transfer
    }
}