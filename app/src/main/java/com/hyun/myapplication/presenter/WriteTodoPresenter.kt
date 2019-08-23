package com.hyun.myapplication.presenter

import android.content.Context
import com.hyun.myapplication.DBHelper.DBHelper
import com.hyun.myapplication.contract.WriteTodoContract
import com.hyun.myapplication.model.Todo

class WriteTodoPresenter : WriteTodoContract.Presenter {
    private var wtView: WriteTodoContract.View? = null

    override fun takeView(view: WriteTodoContract.View) {
        wtView = view
    }

    override fun dropView() {
        wtView = null
    }

    override fun saveTodo(context: Context, dbHelper: DBHelper, todo: Todo) {
        dbHelper.addTodo(todo)
    }

    override fun updateTodo(context: Context, dbHelper: DBHelper, todo: Todo) {
        dbHelper.updateTodo(todo)
    }
}