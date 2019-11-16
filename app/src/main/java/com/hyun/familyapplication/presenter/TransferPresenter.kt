package com.hyun.familyapplication.presenter

import android.content.ContentValues
import android.content.Context
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.TransferContract
import com.hyun.familyapplication.model.APIUtils
import com.hyun.familyapplication.model.FamilyModel
import com.hyun.familyapplication.model.TransferModel
import com.hyun.familyapplication.model.User
import com.hyun.familyapplication.view.Adapter.TransferAdapter

class TransferPresenter : TransferContract.Presenter, TransferContract.Listener {
    private var view: TransferContract.View?= null
    private var adapter:TransferAdapter?=null

    override fun getFamily(context: Context) {
        FamilyModel.getTransfer(context, this)
    }

    override fun takeAdapter(adapter: TransferAdapter) {
        this.adapter = adapter
        adapter.setListener(this)
    }

    override fun takeView(view: TransferContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    // Listener
    override fun onSuccess(result: String, email: String) {
        adapter?.setEmail(email)
        adapter?.setData(FamilyModel.parseArray(result, email))
        adapter?.notifyDataSetChanged()
    }

    override fun onSelected(context: Context, user: User) {
        // 선택된 유저가 생길 때
        TransferModel.selectUser(context,this, user)
    }

    override fun onUserChange(context: Context) {
        val db = DBHelper(context)
        val user = db.getUser()

        val contentValues:ContentValues = ContentValues()
        contentValues.put("email", user?.email)
        contentValues.put("name", user?.name)
        contentValues.put("hoching", user?.hoching)
        contentValues.put("gender", user?.gender)
        contentValues.put("phone", user?.phone)
        contentValues.put("anniversary", user?.anniversary)
        contentValues.put("room", "")

        val json = APIUtils.makeJson(contentValues)
        val url = context.getString(R.string.url) + "user/"+user?.email+"/"

        APIUtils.putUserAsyncTask(context, this).execute(url, json)
    }

    override fun onUserChangeSQLite(context: Context, result:String) {
        TransferModel.changeUserSQLite(context, result)
        onSuccess()
    }

    override fun onSuccess() {
        view?.mainActivity()
    }
}