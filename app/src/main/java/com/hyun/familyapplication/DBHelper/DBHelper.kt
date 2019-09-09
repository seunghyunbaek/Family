package com.hyun.familyapplication.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.hyun.familyapplication.model.Record
import com.hyun.familyapplication.model.Todo
import com.hyun.familyapplication.model.User

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
        Log.d("jkljkljkl", "디비 onCreate() 실행")
        val CREATE_TABLE_RECORD =
            ("CREATE TABLE $RECORD_TABLE_NAME ($RECORD_COOL_ID INTEGER PRIMARY KEY, $RECORD_COOL_NAME TEXT, $RECORD_COOL_DATE TEXT, $RECORD_COOL_CONTENT TEXT)")
        val CREATE_TABLE_TODO =
            ("CREATE TABLE $TODO_TABLE_NAME ($TODO_COOL_ID INTEGER PRIMARY KEY, $TODO_COOL_TITLE TEXT, $TODO_COOL_DATE TEXT)")
        val CREATE_TABLE_USER =
            ("CREATE TABLE $PROFILE_TABLE_NAME ($PROFILE_COOL_EMAIL TEXT PRIMARY KEY, $PROFILE_COOL_NAME TEXT, $PROFILE_COOL_HOCHING TEXT, $PROFILE_COOL_GENDER TEXT, $PROFILE_COOL_PHONE TEXT, $PROFILE_COOL_ANNIVERSARY TEXT)")

        db!!.execSQL(CREATE_TABLE_RECORD)
        db!!.execSQL(CREATE_TABLE_TODO)
        db!!.execSQL(CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        if (db != null) {

        Log.d("jkljkljkl", "디비 onUpgrade() 실행")

        db!!.execSQL("DROP TABLE IF EXISTS $RECORD_TABLE_NAME")
        db!!.execSQL("DROP TABLE IF EXISTS $TODO_TABLE_NAME")
        db!!.execSQL("DROP TABLE IF EXISTS $PROFILE_TABLE_NAME")
        onCreate(db)
//        }
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        super.onDowngrade(db, oldVersion, newVersion)

        Log.d("jkljkljkl", "디비 onDowngrade() 실행")

        db!!.execSQL("DROP TABLE IF EXISTS $RECORD_TABLE_NAME")
        db!!.execSQL("DROP TABLE IF EXISTS $TODO_TABLE_NAME")
        db!!.execSQL("DROP TABLE IF EXISTS $PROFILE_TABLE_NAME")
        onCreate(db)
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

    // [START User TABLE]

    fun getUser(email: String): User? {
        val selectQueryHandler =
            "SELECT * FROM $PROFILE_TABLE_NAME WHERE $PROFILE_COOL_EMAIL='$email'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQueryHandler, null)
        val user = User();

        if (cursor.moveToFirst()) {
            do {
                user.email = cursor.getString(cursor.getColumnIndex(PROFILE_COOL_EMAIL))
                user.name = cursor.getString(cursor.getColumnIndex(PROFILE_COOL_NAME))
                user.hoching = cursor.getString(cursor.getColumnIndex(PROFILE_COOL_HOCHING))
                user.gender = cursor.getString(cursor.getColumnIndex(PROFILE_COOL_GENDER))
                user.phone = cursor.getString(cursor.getColumnIndex(PROFILE_COOL_PHONE))
                user.anniversary = cursor.getString(cursor.getColumnIndex(PROFILE_COOL_ANNIVERSARY))
            } while (cursor.moveToNext())
        }
        db.close()

        if (user.email.equals("")) {
            return null
        }

        return user
    }

    fun addUser(user: User) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(PROFILE_COOL_EMAIL, user.email)
        values.put(PROFILE_COOL_NAME, user.name)
        values.put(PROFILE_COOL_HOCHING, user.hoching)
        values.put(PROFILE_COOL_GENDER, user.gender)
        values.put(PROFILE_COOL_PHONE, user.phone)
        values.put(PROFILE_COOL_ANNIVERSARY, user.anniversary)
        db.insert(PROFILE_TABLE_NAME, null, values)
//        val newUser: User? = user.email?.let { getUser(it) }

//        if (newUser != null) {
//            if (newUser.email == "") return
//
//            val values = ContentValues()
//            values.put(PROFILE_COOL_EMAIL, newUser.email)
//            values.put(PROFILE_COOL_NAME, newUser.name)
//            values.put(PROFILE_COOL_HOCHING, newUser.hoching)
//            values.put(PROFILE_COOL_GENDER, newUser.gender)
//            values.put(PROFILE_COOL_PHONE, newUser.phone)
//            values.put(PROFILE_COOL_ANNIVERSARY, newUser.anniversary)
//            db.insert(PROFILE_TABLE_NAME, null, values)
//        }
        db.close()
    }


    // [END User TABLE]

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

        private val PROFILE_TABLE_NAME = "User"
        private val PROFILE_COOL_EMAIL = "Email"
        private val PROFILE_COOL_NAME = "Id"
        private val PROFILE_COOL_HOCHING = "Hoching"
        private val PROFILE_COOL_GENDER = "Gender"
        private val PROFILE_COOL_PHONE = "Phone"
        private val PROFILE_COOL_ANNIVERSARY = "Anniversary"
    }

}
