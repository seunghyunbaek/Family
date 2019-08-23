package com.hyun.myapplication.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.hyun.myapplication.model.Record
import com.hyun.myapplication.model.Todo

class DBHelper(
    context: Context,
    tableName: String
) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME, null,
        DATABASE_VER
    ) {

    var tableName = tableName
//    var CREATE_TABLE_QUERY:String? = when(tableName) {
//        "Record" ->  "Record"
//        else -> null
//    }

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("jkljkljkl", "DBOnCreate()")
//        var CREATE_TABLE_QUERY: String? = null
//        when (tableName) {
//            "Record" -> {
        val CREATE_TABLE_RECORD =
            ("CREATE TABLE $RECORD_TABLE_NAME ($RECORD_COOL_ID INTEGER PRIMARY KEY, $RECORD_COOL_NAME TEXT, $RECORD_COOL_DATE TEXT, $RECORD_COOL_CONTENT TEXT)")
//            }
//            "Todo" -> {
        val CREATE_TABLE_TODO =
            ("CREATE TABLE $TODO_TABLE_NAME ($TODO_COOL_ID INTEGER PRIMARY KEY, $TODO_COOL_TITLE TEXT, $TODO_COOL_DATE TEXT)")
//            }
//        }
        db!!.execSQL(CREATE_TABLE_RECORD)
        db!!.execSQL(CREATE_TABLE_TODO)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        when (tableName) {
//            "Record" -> {
        db!!.execSQL("DROP TABLE IF EXISTS $RECORD_TABLE_NAME")
//            }
//            "Todo" -> {
        db!!.execSQL("DROP TABLE IF EXISTS $TODO_TABLE_NAME")
//            }
//        }
        onCreate(db!!)
    }

    // [START Record TABLE]
    val allRecord: List<Record>
        get() {
            val lstRecord = ArrayList<Record>()
            val selectQueryHandler = "SELECT * FROM $RECORD_TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQueryHandler, null)
            if (cursor.moveToFirst()) {
                do {
                    val record = Record()
                    record.id = cursor.getInt(cursor.getColumnIndex(RECORD_COOL_ID))
                    record.name = cursor.getString(cursor.getColumnIndex(RECORD_COOL_NAME))
                    record.date = cursor.getString(cursor.getColumnIndex(RECORD_COOL_DATE))
                    record.content = cursor.getString(cursor.getColumnIndex(RECORD_COOL_CONTENT))

                    lstRecord.add(record)
                } while (cursor.moveToNext())
            }
            db.close()
            return lstRecord
        }

    fun addRecord(record: Record) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(RECORD_COOL_ID, record.id)
        values.put(RECORD_COOL_NAME, record.name)
        values.put(RECORD_COOL_DATE, record.date)
        values.put(RECORD_COOL_CONTENT, record.content)

        db.insert(RECORD_TABLE_NAME, null, values)
        db.close()
    }

    fun updateRecord(record: Record): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(RECORD_COOL_ID, record.id)
        values.put(RECORD_COOL_NAME, record.name)
        values.put(RECORD_COOL_DATE, record.date)
        values.put(RECORD_COOL_CONTENT, record.content)

        return db.update(
            RECORD_TABLE_NAME,
            values,
            "$RECORD_COOL_ID=?",
            arrayOf(record.id.toString())
        )
    }

    fun deleteRecord(record: Record) {
        val db = this.writableDatabase

        db.delete(RECORD_TABLE_NAME, "$RECORD_COOL_ID=?", arrayOf(record.id.toString()))
        db.close()
    }
    // [END Record TABLE]

    // [START Todo TABLE]
    val allTodo: List<Todo>
        get() {
            val lstTodo = ArrayList<Todo>()
            val selectQueryHandler = "SELECT * FROM $TODO_TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQueryHandler, null)
            if (cursor.moveToFirst()) {
                do {
                    val todo = Todo()
                    todo.id = cursor.getInt(cursor.getColumnIndex(TODO_COOL_ID))
                    todo.title = cursor.getString(cursor.getColumnIndex(TODO_COOL_TITLE))
                    todo.date = cursor.getString(cursor.getColumnIndex(TODO_COOL_DATE))

                    lstTodo.add(todo)
                } while (cursor.moveToNext())
            }

            db.close()
            return lstTodo
        }

    fun addTodo(todo: Todo) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TODO_COOL_ID, todo.id)
        values.put(TODO_COOL_TITLE, todo.title)
        values.put(TODO_COOL_DATE, todo.date)

        db.insert(TODO_TABLE_NAME, null, values)
        db.close()
    }

    fun updateTodo(todo: Todo): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TODO_COOL_ID, todo.id)
        values.put(TODO_COOL_TITLE, todo.title)
        values.put(TODO_COOL_DATE, todo.date)

        return db.update(TODO_TABLE_NAME, values, "$TODO_COOL_ID=?", arrayOf(todo.id.toString()))
    }

    fun deleteTodo(todo: Todo) {
        val db = this.writableDatabase

        db.delete(TODO_TABLE_NAME, "$TODO_COOL_ID=?", arrayOf(todo.id.toString()))
        db.close()
    }
    // [END Todo TABLE]

    companion object {
        private val DATABASE_NAME = "KFAMILY.db"
        private val DATABASE_VER = 1

        private val RECORD_TABLE_NAME = "Record"
        private val RECORD_COOL_ID = "Id"
        private val RECORD_COOL_NAME = "Name"
        private val RECORD_COOL_DATE = "Date"
        private val RECORD_COOL_CONTENT = "Content"

        private val TODO_TABLE_NAME = "Todo"
        private val TODO_COOL_ID = "Id"
        private val TODO_COOL_TITLE = "Title"
        private val TODO_COOL_DATE = "Date"
    }
}
