package com.hyun.familyapplication.model

import android.content.ContentValues
import android.content.Context
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.FindContract

class FindModel {
    companion object {
        fun findUser(context: Context, email:String, listener:FindContract.Listener) {
            val url = context.getString(R.string.url) + "user/" + email.replace(".","_") + "/"
            APIUtils.getFindAsyncTask(listener).execute(url)
        }

        fun inviteUser(url:String, listener: FindContract.Listener, room:Int, inviter:String, guest:String) {
            val inviteurl = url + "invite/"
            val cv = ContentValues()
            cv.put("room", room)
            cv.put("inviter", inviter)
            cv.put("guest", guest)

            val json = APIUtils.makeJson(cv)

            APIUtils.postInviteAsyncTask(listener).execute(inviteurl, json)
        }
    }
}