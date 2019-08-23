package com.hyun.myapplication.view.Adapter

import android.content.Context
import android.content.Intent
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hyun.myapplication.DBHelper.DBHelper
import com.hyun.myapplication.R
import com.hyun.myapplication.model.Record
import com.hyun.myapplication.view.Activity.WriteRecordActivity
import kotlinx.android.synthetic.main.item_record.view.*

class RecordAdapter(
    internal val context: Context?,
    internal var lstRecord: List<Record>
) : RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecordViewHolder(parent)

    override fun getItemCount(): Int = lstRecord.size

    override fun onBindViewHolder(holder: RecordAdapter.RecordViewHolder, position: Int) {

        lstRecord[position].let { item ->
            with(holder) {
                tvTitle.text = item.name
                tvContent.text = item.content
            }


//            holder.itemView.setOnClickListener {
//                //                    tvTitle.text = "hi"
//
//                val record = Record()
//                record.id = lstRecord[position].id
//                record.name = lstRecord[position].name
//                record.date = lstRecord[position].date
//                record.content = lstRecord[position].content
//
//                val db = RecordDBHelper(context)
//                db.deleteRecord(record)
//
//                lstRecord = db.allRecord
//
//                this.notifyDataSetChanged()
//            }


        }
    }

    inner class RecordViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_record, parent, false)
    ), View.OnCreateContextMenuListener {
        val tvTitle = itemView.item_text_title_record
        val tvContent = itemView.tv_main_content
//        val itemposition = adapterPosition

        val menu = itemView.setOnCreateContextMenuListener(this)

        val onMenuItemClickListener: MenuItem.OnMenuItemClickListener =
            MenuItem.OnMenuItemClickListener() {
                when (it.itemId) {
                    R.id.update_menu -> {
                        val intent: Intent = Intent(context, WriteRecordActivity::class.java)
                        intent.putExtra("id", lstRecord[adapterPosition].id)
                        intent.putExtra("name", lstRecord[adapterPosition].name)
                        intent.putExtra("date", lstRecord[adapterPosition].date)
                        intent.putExtra("content", lstRecord[adapterPosition].content)
                        context!!.startActivity(intent)
//                        Toast.makeText(context, "update", Toast.LENGTH_SHORT).show()
                        return@OnMenuItemClickListener true
                    }

                    R.id.delete_menu -> {
//                        val record = Record()
//                        record.id = lstRecord[adapterPosition].id
//                        record.name = lstRecord[adapterPosition].name
//                        record.date = lstRecord[adapterPosition].date
//                        record.content = lstRecord[adapterPosition].content

                        val record = lstRecord[adapterPosition]

                        val db = DBHelper(context!!, "Record")
                        db.deleteRecord(record)

                        lstRecord = db.allRecord
                        notifyDataSetChanged()
                        Toast.makeText(context, "삭제 되었습니다", Toast.LENGTH_SHORT).show()
                        return@OnMenuItemClickListener true
                    }
                }
                return@OnMenuItemClickListener false
            }


        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            var update: MenuItem = menu!!.add(Menu.NONE, R.id.update_menu, 1, "수정")
            var del: MenuItem = menu!!.add(Menu.NONE, R.id.delete_menu, 2, "삭제")

            update.setOnMenuItemClickListener(onMenuItemClickListener)
            del.setOnMenuItemClickListener(onMenuItemClickListener)
        }
    }
}