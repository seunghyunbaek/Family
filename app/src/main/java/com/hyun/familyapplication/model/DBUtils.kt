package com.hyun.familyapplication.model

import android.content.ContentValues
import android.content.Context
import android.os.Build
import com.hyun.familyapplication.DBHelper.DBHelper
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DBUtils {
    companion object {
        // Room 생성하기
        fun CreateRoom(context: Context): Int { // 유저가 새로운 기록 Room을 만든다
            val dbHelper = DBHelper(context)
            val user = dbHelper.getUser()

            if (user?.roomId == 0) {
                val contentValues = ContentValues()
                contentValues.put("email", user?.email)
                dbHelper.addRoom(contentValues)
                val contentValues2 = dbHelper.getRoom(user?.email)
                user.roomId = contentValues2?.getAsInteger("roomid")

                val userValues = ContentValues()
                userValues.put(PROFILE_COOL_EMAIL, user.email)
                userValues.put(PROFILE_COOL_NAME, user.name)
                userValues.put(PROFILE_COOL_HOCHING, user.hoching)
                userValues.put(PROFILE_COOL_GENDER, user.gender)
                userValues.put(PROFILE_COOL_PHONE, user.phone)
                userValues.put(PROFILE_COOL_ANNIVERSARY, user.anniversary)
                userValues.put(PROFILE_COOL_ROOMID, user.roomId)

                val result = dbHelper.updateUserRoom(userValues)
                return result
            }
            return -1
        }

        // Room 체크하기
        fun checkRoom(context: Context): Boolean {
            val dbHelper = DBHelper(context)
            val user = dbHelper.getUser()

            if (user?.roomId == 0)
                return false
            else
                return true
        }

        // Record 저장하기
        fun saveRecord(context: Context, contentValues: ContentValues): Long {
            val dbHelper = DBHelper(context)
            val user = dbHelper.getUser()
            val values = contentValues // content 들어있음

            // email
            values.put("$RECORD_COOL_EMAIL", user?.email)
            // name
            values.put("$RECORD_COOL_NAME", user?.name)
            // date
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                var current = LocalDate.now()
                var formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
                var date = current.format(formatter)
                values.put("$RECORD_COOL_DATE", date)
            } else {
                var simpleDateFormatter = SimpleDateFormat("yyyyMMdd")
                var date = simpleDateFormatter.format(Date())
                values.put("$RECORD_COOL_DATE", date)
            }
            // roomid
            values.put("$RECORD_COOL_ROOMID", user?.roomId)
            val result = dbHelper.addRecord(values)

            return result
        }

        fun saveRecordImages(context: Context, recordid: Long, uriList: MutableList<String>?) {
            val recordid = recordid
            val dbHelper = DBHelper(context)
            if (uriList != null) {
                for (uri in uriList) {
                    val values = ContentValues()
                    values.put("$IMAGE_COOL_RECORDID", recordid)
                    values.put("$IMAGE_COOL_URL", uri)
                    dbHelper.addRecordImage(values)
                }
            }
        }

        fun getImageList(key:Int, lst:List<RecordImage>):List<String> {
            val rslt = ArrayList<String>()
            for(str in lst) {
                if(key == str.recordid)
                    rslt.add(str.uri)
            }
            return rslt
        }

        private val RECORD_TABLE_NAME = "Record"
        private val RECORD_COOL_ID = "Id"
        private val RECORD_COOL_EMAIL = "Email"
        private val RECORD_COOL_NAME = "Name"
        private val RECORD_COOL_DATE = "Date"
        private val RECORD_COOL_CONTENT = "Content"
        private val RECORD_COOL_ROOMID = "RoomId"

        private val TODO_TABLE_NAME = "Todo"
        private val TODO_COOL_ID = "Id"
        private val TODO_COOL_TITLE = "Title"
        private val TODO_COOL_DATE = "Date"

        private val PROFILE_TABLE_NAME = "User"
        private val PROFILE_COOL_EMAIL = "Email"
        private val PROFILE_COOL_NAME = "Name"
        private val PROFILE_COOL_HOCHING = "Hoching"
        private val PROFILE_COOL_GENDER = "Gender"
        private val PROFILE_COOL_PHONE = "Phone"
        private val PROFILE_COOL_ANNIVERSARY = "Anniversary"
        private val PROFILE_COOL_ROOMID = "RoomId"

        private val ROOM_TABLE_NAME = "Room"
        private val ROOM_COOL_ID = "Id"
        private val ROOM_COOL_EMAIL = "Email"

        private val IMAGE_TABLE_NAME = "RecordImage"
        private val IMAGE_COOL_ID = "ID"
        private val IMAGE_COOL_RECORDID = "RecordId"
        private val IMAGE_COOL_URL = "Url"
    }
}