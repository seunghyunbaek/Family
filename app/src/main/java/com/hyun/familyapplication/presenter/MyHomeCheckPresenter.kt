package com.hyun.familyapplication.presenter

import android.content.Context
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.contract.MyHomeCheckContract
import com.hyun.familyapplication.model.DBUtils

class MyHomeCheckPresenter : MyHomeCheckContract.Presenter {

    private var myHomeCheckView: MyHomeCheckContract.View? = null

    override fun takeView(view: MyHomeCheckContract.View) {
        myHomeCheckView = view
    }

    override fun dropView() {
        myHomeCheckView = null
    }

    override fun createRoom(context:Context) {

        val result = DBUtils.CreateRoom(context)
        if(result == 1)
            myHomeCheckView?.createdRoom()
    }

    override fun checkRoom(context: Context) {
        val result = DBUtils.checkRoom(context)
        if(result)
            myHomeCheckView?.createdRoom()
    }
}