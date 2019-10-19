package com.hyun.familyapplication.view.Activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.DatePicker
import android.widget.TimePicker
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.WriteTodoContract
import com.hyun.familyapplication.model.Todo
import com.hyun.familyapplication.presenter.WriteTodoPresenter
import kotlinx.android.synthetic.main.activity_write_todo.*
import java.util.*

class WriteTodoActivity : BaseActivity(), WriteTodoContract.View {

    private lateinit var mPresenter: WriteTodoPresenter
    internal lateinit var db: DBHelper
    var todo: Todo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_todo)

        mPresenter.takeView(this)
//        db = DBHelper(this, TABLE_NAME)
        db = DBHelper(this)

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
            text_date_write_todo.text = todo!!.date
        }

        image_back_write_todo.setOnClickListener { onBackPressed() }

        image_calendar_write_todo.setOnClickListener {
            var calendar = Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var day = calendar.get(Calendar.DAY_OF_MONTH)

            var date_listener = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    text_date_write_todo.text = "${year}년 ${month + 1}월 ${dayOfMonth}일"
                }
            }

            var builder = DatePickerDialog(this, date_listener, year, month, day)
            builder.show()
        }

        text_time_write_todo.setOnClickListener {
            var time = Calendar.getInstance()
            var hour = time.get(Calendar.HOUR)
            var minute = time.get(Calendar.MINUTE)

            var timeListener = object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    text_time_write_todo.text = "${hourOfDay}시 ${minute}분"
                }
            }

            var builder = TimePickerDialog(this, timeListener, hour, minute, false)
            builder.show()
        }

        text_save_write_todo.setOnClickListener {
            if (todo == null) {
                val todo = Todo()
                if (db.allTodo.size == 0)
                    todo.id = 0
                else
                    todo.id = db.allTodo.last().id + 1
                todo.title = edit_write_todo.text.toString()
                todo.date = text_date_write_todo.text.toString()
//                todo.date = "2019년 8월 10일"

                mPresenter.saveTodo(this, db, todo)
            } else {
                todo!!.title = edit_write_todo.text.toString()
                todo!!.date = text_date_write_todo.text.toString()
                mPresenter.updateTodo(this, db, todo!!)
            }
            finish()
        }
    }

    override fun initPresenter() {
        mPresenter = WriteTodoPresenter()
    }

    override fun successTodo() {
    }

    override fun showError(error: String) {
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.dropView()
    }

    companion object {
        private val TABLE_NAME = "Todo"
    }
}
