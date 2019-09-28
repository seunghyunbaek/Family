package com.hyun.familyapplication.presenter

import android.content.Context
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.contract.MyHomeCheckContract
import com.hyun.familyapplication.model.DBUtils
import com.hyun.familyapplication.model.MyHomeCheckModel

class MyHomeCheckPresenter : MyHomeCheckContract.Presenter,MyHomeCheckContract.onMyHomeCheckListener {

    private var myHomeCheckView: MyHomeCheckContract.View? = null
    private var myHomeCheckModel:MyHomeCheckModel

    constructor() {
        myHomeCheckModel = MyHomeCheckModel(this)
    }

    override fun takeView(view: MyHomeCheckContract.View) {
        myHomeCheckView = view
    }

    override fun dropView() {
        myHomeCheckView = null
    }

    override fun createRoom(context:Context) {
        myHomeCheckModel.createRoomInServer(context)
    }

    override fun checkRoom(context: Context) {
        val result = DBUtils.checkRoom(context)
        if(result)
            myHomeCheckView?.createdRoom()
    }


    ////////////////////////

    override fun onSuccess(email: String) {
    }

    override fun onFailure() {
    }

    override fun onEnd(context: Context, result:String) {
        val result = DBUtils.CreateRoom(context, result)
        if(result == 1)
            myHomeCheckView?.createdRoom()
    }
}