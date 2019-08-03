package com.hyun.myapplication.contract

import com.hyun.myapplication.presenter.BasePresenter
import com.hyun.myapplication.view.BaseView

interface PictureContract {
    interface View: BaseView {
        fun showLoading()
        fun hideLoading()
        fun showPictures()
    }

    interface Presenter: BasePresenter<View> {
        // 이미지 목록가져오기
        fun getPictures()
        // 이미지 추가하기
        fun updatePictures()
    }
}