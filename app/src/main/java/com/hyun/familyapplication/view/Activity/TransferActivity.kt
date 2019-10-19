package com.hyun.familyapplication.view.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.TransferContract
import com.hyun.familyapplication.presenter.TransferPresenter
import com.hyun.familyapplication.view.Adapter.TransferAdapter
import kotlinx.android.synthetic.main.activity_transfer.*

class TransferActivity : BaseActivity(), TransferContract.View {

    private var mPresenter:TransferPresenter?= null
    private var adapter:TransferAdapter?= null
    private var recyclerview:RecyclerView?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)

        mPresenter?.takeView(this)

        recyclerview_transfer.layoutManager = LinearLayoutManager(this)!!
        adapter = TransferAdapter()

        mPresenter?.takeAdapter(adapter!!)
        adapter?.setDialog(this)
        recyclerview_transfer.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        if(mPresenter==null)
            initPresenter()
        refreshData()
    }

    override fun onDestroy() {
        mPresenter?.dropView()
        super.onDestroy()
    }

    override fun initPresenter() {
        mPresenter = TransferPresenter()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun transferActivity() {

    }

    override fun showError(error: String) {
    }

    fun refreshData() {
        mPresenter?.getFamily(this)
    }
}
