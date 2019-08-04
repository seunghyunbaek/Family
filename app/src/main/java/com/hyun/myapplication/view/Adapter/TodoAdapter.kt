package com.hyun.myapplication.view.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyun.myapplication.R
import com.hyun.myapplication.model.MainData
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    var items: MutableList<MainData> = mutableListOf(
        MainData("Title1", "Content1"),
        MainData("Title2", "Content2"), MainData("Title3", "Content3"),
        MainData("Title4", "Content4"), MainData("Title5", "Content5"),
        MainData("Title6", "Content6"), MainData("Title7", "Content7"),
        MainData("Title8", "Content8"), MainData("Title9", "Content9"),
        MainData("Title10", "Content10"), MainData("Title11", "Content11"),
        MainData("Title12", "Content12"), MainData("Title13", "Content13")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder = TodoViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        items[position].let { item ->
            with(holder) {
                tvTitleTodo.text = item.title
                tvTimeTodo.text = "2019.08.04 12:22 까지"
            }
        }
    }

    inner class TodoViewHolder(parent:ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
    ) {
        val imgProfileTodo = itemView.imgProfileTodo
        val tvTitleTodo = itemView.tvTitleTodo
        val tvTimeTodo = itemView.tvTimeTodo
    }
}