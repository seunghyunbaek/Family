package com.hyun.familyapplication.view.Adapter

import android.content.Context
import android.content.Intent
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.model.Record
import com.hyun.familyapplication.model.RecordImage
import com.hyun.familyapplication.view.Activity.WriteRecordActivity
import kotlinx.android.synthetic.main.item_record.view.*

class RecordAdapter(
    internal val context: Context?,
    internal var lstRecord: List<Record>,
    internal var lstImages: List<RecordImage>
) : RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecordViewHolder(parent)

    override fun getItemCount(): Int = lstRecord.size

    override fun onBindViewHolder(holder: RecordAdapter.RecordViewHolder, position: Int) {
        lstRecord[position].let { item ->
            with(holder) {
                tvTitle.text = item.name
                tvContent.text = item.content
//                val rslt = DBUtils.getImageList(item.id, lstImages)

                println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^")
                println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^")
                println("item.record_images?.length():$position")
                println(item.record_images?.length())
                println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^")
                println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^")

                if (item.record_images!!.length() > 0) {
                    var imglist = ArrayList<String>()
                    for (imgobjidx in 0..(item.record_images!!.length() - 1)) {
                        imglist.add(item.record_images!!.getJSONObject(imgobjidx).getString("uri"))
                    }
                    val adapter = SliderAdapter(context!!, imglist)
                    viewPager.adapter = adapter
                    viewPager.visibility = View.VISIBLE
                } else {
                    viewPager.visibility = View.GONE
                }
//                for (ri in lstImages) {
//                    if (ri.record == item.id) {
//                        viewPager.visibility = View.VISIBLE
//                        Glide.with(context!!).load(ri.uri)
//                            .into(viewPager)
//                        break
//                    } else {
//                        viewPager.visibility = View.GONE
//                    }
//                }
            }

        }
    }


    inner class RecordViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_record, parent, false)
    ), View.OnCreateContextMenuListener {
        val tvTitle = itemView.item_text_title_record
        val tvContent = itemView.tv_main_content
        val viewPager = itemView.viewpager_item_record

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

//                        val db = DBHelper(context!!, "Record")
                        val db = DBHelper(context!!)
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