package com.hyun.familyapplication.contract

import android.content.Context
import com.hyun.familyapplication.model.RecordImage
import com.hyun.familyapplication.presenter.BasePresenter
import com.hyun.familyapplication.view.Adapter.PictureAdapter
import com.hyun.familyapplication.view.BaseView

interface PictureContract {
    interface View: BaseView {
        fun showLoading()
        fun hideLoading()
        fun showPictures(list:ArrayList<RecordImage>)
    }

    interface Presenter: BasePresenter<View> {
        // 이미지 목록가져오기
        fun getPictures(context: Context)
        fun takeAdapter(adapter:PictureAdapter)
        fun dropAdapter()
    }

    interface Listener {
        fun onSeccess(result:String)
    }
}