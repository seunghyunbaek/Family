package com.hyun.familyapplication.presenter

import android.content.Context
import com.hyun.familyapplication.contract.PictureContract
import com.hyun.familyapplication.model.APIUtils
import com.hyun.familyapplication.model.PictureModel
import com.hyun.familyapplication.model.RecordImage
import com.hyun.familyapplication.view.Adapter.PictureAdapter

class PicturePresenter : PictureContract.Presenter, PictureContract.Listener {

    private var view: PictureContract.View? = null
    private var adapter: PictureAdapter? = null

    override fun takeView(view: PictureContract.View) {
        this.view = view
    }

    override fun dropView() {
        view = null
    }

    override fun takeAdapter(adapter: PictureAdapter) {
        this.adapter = adapter
    }

    override fun dropAdapter() {
        this.adapter = null
    }

    override fun getPictures(context: Context) {
        PictureModel.getPicture(context, this)
    }

    override fun onSeccess(result: String) {
         adapter?.dataChange(PictureModel.parseArray(result))
    }
}