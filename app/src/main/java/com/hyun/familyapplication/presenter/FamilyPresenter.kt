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

    override fun onExit() {
        if (view != null) {
            view?.transferActivity()
        }
    }
}