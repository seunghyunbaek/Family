package com.hyun.myapplication.contract

import android.content.Context
import com.hyun.myapplication.DBHelper.DBHelper
import com.hyun.myapplication.DBHelper.TodoDBHelper
import com.hyun.myapplication.model.Todo
import com.hyun.myapplication.presenter.BasePresenter
import com.hyun.myapplication.view.BaseView

interface WriteTodoContract {
    interface View : BaseView {
        fun successTodo()
    }

    interface Presenter : BasePresenter<View> {
        fun saveTodo(context: Context, dbHelper: DBHelper, todo: Todo)
        fun updateTodo(context: Context, dbHelper: DBHelper, todo: Todo)
    }
}