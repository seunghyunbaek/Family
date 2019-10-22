package com.hyun.familyapplication.presenter

import android.content.Context
import com.hyun.familyapplication.contract.TransferContract
import com.hyun.familyapplication.model.FamilyModel
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

    override fun onSelected() {
        
    }
}