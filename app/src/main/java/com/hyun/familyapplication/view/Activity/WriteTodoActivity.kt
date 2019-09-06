package com.hyun.familyapplication.view.Activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.WriteTodoContract
import com.hyun.familyapplication.model.Todo
import com.hyun.familyapplication.presenter.WriteTodoPresenter
import kotlinx.android.synthetic.main.activity_write_todo.*

class WriteTodoActivity : BaseActivity(), WriteTodoContract.View {

    private lateinit var mPresenter: WriteTodoPresenter
    internal lateinit var db: DBHelper
    var todo: Todo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_todo)

        mPresenter.takeView(this)
        db = DBHelper(this, TABLE_NAME)

        val intent: Intent = intent
        if (intent.extras != null) {
            todo = Todo()
            val id: Int = intent.extras.getInt("id")
            val title: String = intent.extras.getString("title")
            val date: String = intent.extras.getString("date")

            todo!!.id = id
            todo!!.title = title
            todo!!.date = date

            edit_write_todo.text = Editable.Factory.getInstance().newEditable(title)
        }

        image_back_write_todo.setOnClickListener { onBackPressed() }

        text_save_write_todo.setOnClickListener {
            if (todo == null) {
                val todo = Todo()
                if (db.allTodo.size == 0)
                    todo.id = 0
                else
                    todo.id = db.allTodo.last().id + 1
                todo.title = edit_write_todo.text.toString()
                todo.date = "2019년 8월 10일"

                mPresenter.saveTodo(this, db, todo)
            } else {
                todo!!.title = edit_write_todo.text.toString()
                mPresenter.updateTodo(this, db, todo!!)
            }
            finish()
        }
    }

    override fun initPresenter() {
        mPresenter = WriteTodoPresenter()
    }

    override fun successTodo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.dropView()
    }

    companion object {
        private val TABLE_NAME = "Todo"
    }
}
