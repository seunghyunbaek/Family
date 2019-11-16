package com.hyun.familyapplication.model

import android.content.ContentValues
import android.content.Context
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.InvitedContract
import org.json.JSONObject

class InvitedModel {
    companion object {
        fun getInvited(baseurl:String, context: Context, listener:InvitedContract.Listener) {
            val db = DBHelper(context)
            val url = baseurl + "invite/?guest=" + db.getUser()?.email
            APIUtils.getInvitedAsyncTask(listener).execute(url)
        }

        fun putPositive(context:Context, invite: Invite, listener: InvitedContract.Listener) {
            val url = context.getString(R.string.url) + "user/" + invite.guest + "/"
            val db = DBHelper(context)
            val user = db.getUser()
            val cv = ContentValues()
            cv.put("email", user?.email)
            cv.put("name", user?.name)
            cv.put("hoching", user?.hoching)
            cv.put("gender", user?.gender)
            cv.put("phone", user?.phone)
            cv.put("anniversary", user?.anniversary)
            cv.put("room", invite.room)
            val json = APIUtils.makeJson(cv)
            APIUtils.putInvitedAsyncTask(listener, context).execute(url, json)
        }

        fun updateUser(context:Context, result:String):Int {
            val result = DBUtils.CreateRoom(context, result)
            return result
        }
    }
}