package com.hyun.familyapplication.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.hyun.familyapplication.model.Todo

class TodoDBHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VER) {

    companion object {
        private val DATABASE_VER = 1
        private val DATABASE_NAME = "TODODATABASE.db"

        private val TABLE_NAME = "Todo"
        private val COOL_ID = "Id"
        private val COOL_TITLE = "Title"
        private val COOL_DATE = "Date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY =
            ("CREATE TABLE $TABLE_NAME ($COOL_ID INTEGER PRIMARY KEY, $COOL_TITLE TEXT, $COOL_DATE TEXT)")

        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    val allTodo: List<Todo>
        get() {
            val lstTodo = ArrayList<Todo>()
            val selectQueryHandler = "SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQueryHandler, null)
            if(cursor.moveToFirst()) {
                do{
                    val todo = Todo()
                    todo.id = cursor.getInt(cursor.getColumnIndex(COOL_ID))
                    todo.title = cursor.getString(cursor.getColumnIndex(COOL_TITLE))
                    todo.date = cursor.getString(cursor.getColumnIndex(COOL_DATE))

                    lstTodo.add(todo)
                } while (cursor.moveToNext())
            }

            db.close()
            return lstTodo
        }

    fun addTodo(todo:Todo) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COOL_ID, todo.id)
        values.put(COOL_TITLE, todo.title)
        values.put(COOL_DATE, todo.date)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun updateTodo(todo: Todo):Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COOL_ID, todo.id)
        values.put(COOL_TITLE, todo.title)
        values.put(COOL_DATE, todo.date)

        return db.update(TABLE_NAME, values, "$COOL_ID=?", arrayOf(todo.id.toString()))
    }

    fun deleteTodo(todo:Todo) {
        val db = this.writableDatabase

        db.delete(TABLE_NAME, "$COOL_ID=?", arrayOf(todo.id.toString()))
        db.close()
    }

}