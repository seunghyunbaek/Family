package com.hyun.familyapplication.view.Activity

import android.content.DialogInterface
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AlertDialog.Builder
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.FindContract
import com.hyun.familyapplication.presenter.FindPresenter
import com.hyun.familyapplication.view.Adapter.FindAdapter
import kotlinx.android.synthetic.main.activity_find.*


class FindActivity : BaseActivity(), FindContract.View {

    private var mPresenter: FindContract.Presenter? = null
    private var adapter: FindAdapter? = null
    private var dialogListener = object : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface?, which: Int) {
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    println("다이얼로그 긍정")
                    mPresenter?.inviteUser(getString(R.string.url), adapter?.getRoom()!!, adapter?.getInviter()!!, adapter?.getGuest()!!)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find)

        mPresenter?.takeView(this)

        button_back_find.setOnClickListener {
            onBackPressed()
        }

        edit_find.setOnEditorActionListener { v, actionId, event ->
            var bool: Boolean = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (!edit_find.text.toString().equals("")) {
                    mPresenter?.findUser(this, edit_find.text.toString())
                }
                bool = true
            }
            bool
        }

        button_find_find.setOnClickListener {
            mPresenter?.findUser(this, edit_find.text.toString())
        }
        recyclerview_find.layoutManager = LinearLayoutManager(this)
        adapter = FindAdapter(this)
        adapter?.setDialog(initDialog())
        mPresenter?.setAdapter(adapter!!)
        recyclerview_find.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        if (mPresenter == null)
            initPresenter()
    }

    override fun onDestroy() {
        mPresenter?.dropView()
        super.onDestroy()
    }

    override fun initPresenter() {
        mPresenter = FindPresenter()
    }

    override fun showError(error: String) {
    }

    fun initDialog(): Builder {
        val dialog = Builder(this)
        dialog.setMessage("초대하시겠습니까?")
        dialog.setIcon(R.mipmap.ic_launcher)
        dialog.setPositiveButton("예", dialogListener)
        dialog.setNegativeButton("아니오", dialogListener)

        return dialog
    }

}
