package com.hyun.myapplication.view.Activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import com.hyun.myapplication.DBHelper.TodoDBHelper
import com.hyun.myapplication.R
import com.hyun.myapplication.contract.WriteTodoContract
import com.hyun.myapplication.model.Todo
import com.hyun.myapplication.presenter.WriteTodoPresenter
import kotlinx.android.synthetic.main.activity_write_todo.*

class WriteTodoActivity : BaseActivity(), WriteTodoContract.View {

    private lateinit var wtPresenter: WriteTodoPresenter
    internal lateinit var db: TodoDBHelper
    var todo: Todo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_todo)

        wtPresenter.takeView(this)
        db = TodoDBHelper(this)

        val intent: Intent = intent
        if (intent.extras != null) {
            todo = Todo()
            val id: Int = intent.extras.getInt("id")
            val title: String = intent.extras.getString("title")
            val date: String = intent.extras.getString("date")

            todo!!.id = id
            todo!!.title = title
            todo!!.date = date

            editWT.text = Editable.Factory.getInstance().newEditable(title)
        }

        btnBackWT.setOnClickListener { onBackPressed() }

        btnWriteWT.setOnClickListener {
            if (todo == null) {
                val todo = Todo()
                if (db.allTodo.size == 0)
                    todo.id = 0
                else
                    todo.id = db.allTodo.last().id + 1
                todo.title = editWT.text.toString()
                todo.date = "2019년 8월 10일"

                wtPresenter.saveTodo(this, db, todo)
            } else {
                todo!!.title = editWT.text.toString()
                wtPresenter.updateTodo(this, db, todo!!)
            }
            finish()
        }
    }

    override fun initPresenter() {
        wtPresenter = WriteTodoPresenter()
    }

    override fun successTodo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        super.onDestroy()
        wtPresenter.dropView()
    }
}
