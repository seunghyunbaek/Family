package com.hyun.familyapplication.contract

import android.content.Context
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.model.Todo
import com.hyun.familyapplication.presenter.BasePresenter
import com.hyun.familyapplication.view.BaseView

interface WriteTodoContract {
    interface View : BaseView {
        fun successTodo()
    }

    interface Presenter : BasePresenter<View> {
        fun saveTodo(context: Context, dbHelper: DBHelper, todo: Todo)
        fun updateTodo(context: Context, dbHelper: DBHelper, todo: Todo)
    }
}