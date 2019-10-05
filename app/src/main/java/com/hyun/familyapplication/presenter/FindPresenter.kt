package com.hyun.familyapplication.presenter

import android.content.Context
import com.hyun.familyapplication.contract.FindContract
import com.hyun.familyapplication.model.FindModel
import com.hyun.familyapplication.model.User
import com.hyun.familyapplication.view.Adapter.FindAdapter
import org.json.JSONObject

class FindPresenter : FindContract.Presenter, FindContract.Listener {
    private var view: FindContract.View? = null
    private var adapter: FindAdapter? = null
    override fun takeView(view: FindContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun setAdapter(adapter: FindAdapter) {
        this.adapter = adapter
    }

    override fun findUser(context: Context, email: String) {
        FindModel.findUser(context, email, this)
    }

    // Listener
    override fun onSuccess(result: String) {
        var list = ArrayList<User>()
        val jsonObj = JSONObject(result)
        val user = User()
        user.name = jsonObj.getString("name")
        try {
            user.room = jsonObj.getInt("room")
        } catch (e: Exception) {
            user.room = 0
        }
        user.gender = jsonObj.getString("gender")
        user.phone = jsonObj.getString("phone")
        user.anniversary = jsonObj.getString("anniversary")
        user.hoching = jsonObj.getString("hoching")
        user.email = jsonObj.getString("email")
        list.add(user)
        adapter?.setData(list)
    }
}