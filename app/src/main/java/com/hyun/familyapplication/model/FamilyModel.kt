package com.hyun.familyapplication.model

import android.content.ContentValues
import android.content.Context
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.FamilyContract
import com.hyun.familyapplication.contract.TransferContract
import org.json.JSONArray

class FamilyModel {
    companion object {
        fun getFamily(context: Context, listener: FamilyContract.Listener) {
            val dbHelper = DBHelper(context)
            val user = dbHelper.getUser()
            val url = context.getString(R.string.url) + "user/?room=" + user?.room

            APIUtils.getFamilyUserAsyncTask(listener, user?.email).execute(url)
        }

        fun getTransfer(context: Context, listener: TransferContract.Listener) {
            val dbHelper = DBHelper(context)
            val user = dbHelper.getUser()
            val url = context.getString(R.string.url) + "user/?room=" + user?.room

            APIUtils.getTransferUserAsyncTask(listener, user?.email).execute(url)
        }

        fun parseArray(result: String): ArrayList<User> {
            val jsonArray = JSONArray(result)
            val len = jsonArray.length()
            var cnt: Int = 0

            var list = ArrayList<User>()

            while (cnt < len) {

                val jsonObject = jsonArray.getJSONObject(cnt)
                val user = User()
                user.room = jsonObject.getInt("room")
                user.name = jsonObject.getString("name")
                user.email = jsonObject.getString("email")
                user.hoching = jsonObject.getString("hoching")
                user.anniversary = jsonObject.getString("anniversary")
                user.phone = jsonObject.getString("phone")
                user.gender = jsonObject.getString("gender")

                list.add(user)
                cnt++
            }

            return list
        }

        fun parseArray(result: String, email: String): ArrayList<User> {
            val jsonArray = JSONArray(result)
            val len = jsonArray.length()
            var cnt: Int = 0

            var list = ArrayList<User>()

            while (cnt < len) {

                val jsonObject = jsonArray.getJSONObject(cnt)
                val user = User()
                user.room = jsonObject.getInt("room")
                user.name = jsonObject.getString("name")
                user.email = jsonObject.getString("email")
                user.hoching = jsonObject.getString("hoching")
                user.anniversary = jsonObject.getString("anniversary")
                user.phone = jsonObject.getString("phone")
                user.gender = jsonObject.getString("gender")
                if (!email.equals(user.email))
                    list.add(user)
                cnt++
            }

            return list
        }

        fun deleteRoom(context: Context, listener: FamilyContract.Listener) {
            val db = DBHelper(context)
            val user = db.getUser()
            val url = context.getString(R.string.url) + "room/" + user?.room + "/"

            APIUtils.delRoomAsyncTask(context, listener).execute(url)
        }

        fun delSuccess(context: Context) {
            val db = DBHelper(context)
            val user = db.getUser()!!
            val cv = ContentValues()
            cv.put("email", user.email)
            cv.put("name", user.name)
            cv.put("hoching", user.hoching)
            cv.put("gender", user.gender)
            cv.put("phone", user.phone)
            cv.put("anniversary", user.anniversary)
            cv.put("room", 0)

            db.updateUserRoom(cv)
        }
    }
}