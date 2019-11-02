package com.hyun.familyapplication.presenter

import android.content.Context
import com.hyun.familyapplication.contract.FamilyContract
import com.hyun.familyapplication.model.FamilyModel
import com.hyun.familyapplication.view.Adapter.FamilyAdapter

class FamilyPresenter : FamilyContract.Presenter, FamilyContract.Listener {

    private var view: FamilyContract.View? = null
    private var adapter: FamilyAdapter? = null

    override fun takeView(view: FamilyContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun takeAdapter(adapter: FamilyAdapter) {
        this.adapter = adapter
        adapter.setListener(this)
    }

    override fun getFamily(context: Context) {
        FamilyModel.getFamily(context, this)
    }

    override fun findFamily() {
    }


    // Listener
    override fun onSuccess(result: String, email: String) {
        adapter?.setData(FamilyModel.parseArray(result))
        adapter?.setEmail(email)
        adapter?.notifyDataSetChanged()
    }

    override fun onDeleteRoom(context: Context) {
        FamilyModel.deleteRoom(context, this)
    }

    override fun onDelSuccess(context: Context) {
        FamilyModel.delSuccess(context)
        view?.mainActivity()
    }

    override fun onExit(context: Context) {
        FamilyModel.getHost(context, this)
//        if (view != null) {
//            view?.transferActivity()
//        }
    }

    override fun getHost(context: Context) {
        FamilyModel.getHost(context, this)
    }

    override fun onExitRoom(context:Context, result:String, bool:Boolean) {
        FamilyModel.changeUserSQLite(context, result)
        if(!bool) {
            view?.mainActivity()
        }
    }

    override fun getHostResult(context: Context, result: String) {
        val bool = FamilyModel.mainOrTransfer(context, result)

        // true - 주인
        if(bool) {
            view?.transferActivity()
        } else {
            FamilyModel.updateUserRoom(context, this, bool)
        }


//        view?.exitRoom(bool)
    }
}