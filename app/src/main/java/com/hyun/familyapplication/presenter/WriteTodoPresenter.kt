package com.hyun.familyapplication.presenter

import android.content.Context
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.contract.WriteTodoContract
import com.hyun.familyapplication.model.Todo

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