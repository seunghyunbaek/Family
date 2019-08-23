package com.hyun.myapplication.view.Adapter

import android.content.Context
import android.content.Intent
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.hyun.myapplication.DBHelper.DBHelper
import com.hyun.myapplication.R
import com.hyun.myapplication.model.Todo
import com.hyun.myapplication.view.Activity.WriteTodoActivity
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdapter(
    internal val context: Context?,
    internal var lstTodo: List<Todo>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder =
        TodoViewHolder(parent)

    override fun getItemCount(): Int = lstTodo.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        lstTodo[position].let { item ->
            with(holder) {
                tvTitleTodo.text = item.title
                tvTimeTodo.text = item.date
            }
        }
    }

    inner class TodoViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
    ), View.OnCreateContextMenuListener {
        val imgProfileTodo = itemView.imgProfileTodo
        val tvTitleTodo = itemView.tvTitleTodo
        val tvTimeTodo = itemView.tvTimeTodo

        val menu = itemView.setOnCreateContextMenuListener(this)

        val onMenuItemClickListener: MenuItem.OnMenuItemClickListener =
            MenuItem.OnMenuItemClickListener() {
                when (it.itemId) {
                    R.id.update_menu -> {
                        val intent: Intent = Intent(context, WriteTodoActivity::class.java)
                        intent.putExtra("id", lstTodo[adapterPosition].id)
                        intent.putExtra("title", lstTodo[adapterPosition].title)
                        intent.putExtra("date", lstTodo[adapterPosition].date)
                        context!!.startActivity(intent)

                        return@OnMenuItemClickListener true
                    }

                    R.id.delete_menu -> {
                        val todo = lstTodo[adapterPosition]

                        val db = DBHelper(context!!, "Todo")
//                        val db = TodoDBHelper(context)
                        db.deleteTodo(todo)

                        lstTodo = db.allTodo
                        notifyDataSetChanged()

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