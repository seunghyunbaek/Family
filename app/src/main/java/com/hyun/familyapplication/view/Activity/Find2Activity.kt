package com.hyun.familyapplication.view.Activity

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.Find2Contract
import com.hyun.familyapplication.presenter.Find2Presenter
import com.hyun.familyapplication.view.Adapter.Find2Adapter
import kotlinx.android.synthetic.main.activity_find2.*

class Find2Activity : BaseActivity(), Find2Contract.View, View.OnClickListener {

    private var presenter: Find2Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find2)

        edit_find2.setOnEditorActionListener { v, actionId, event ->
            var bool = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (!edit_find2.text.toString().equals("")) {
                    presenter?.find(getString(R.string.url))
                }
                bool = true
            }

            bool
        }
        recyclerview_find2.layoutManager = LinearLayoutManager(this)
        val adapter = Find2Adapter(this)
        recyclerview_find2.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        if (presenter == null) {
            initPresenter()
        }
    }

    override fun onDestroy() {
        presenter?.dropView()
        super.onDestroy()
    }

    override fun initPresenter() {
        presenter = Find2Presenter()
        presenter?.takeView(this)
    }

    override fun showError(error: String) {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_back_find2 -> {
                onBackPressed()
            }
            R.id.button_find_find2 -> {
                edit_find2.text.toString()
                presenter?.find(getString(R.string.url))
            }
        }
    }
}
