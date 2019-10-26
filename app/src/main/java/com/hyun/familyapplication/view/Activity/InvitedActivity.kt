package com.hyun.familyapplication.view.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.InvitedContract
import com.hyun.familyapplication.presenter.InvitedPresenter
import com.hyun.familyapplication.view.Adapter.InvitedAdapter
import kotlinx.android.synthetic.main.activity_invited.*

class InvitedActivity : BaseActivity(), InvitedContract.View {

    var presenter:InvitedContract.Presenter? = null
    var adapter:InvitedAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invited)

        presenter?.takeView(this)

        recyclerview_invited.layoutManager = LinearLayoutManager(this)
        adapter = InvitedAdapter()
        adapter?.setContext(this)
        presenter?.setAdapter(adapter!!)
        recyclerview_invited.adapter = adapter
    }

    override fun initPresenter() {
        presenter = InvitedPresenter()
    }

    override fun onStart() {
        super.onStart()
        if(presenter == null)
            initPresenter()
        presenter?.getInvited(getString(R.string.url), this)
    }

    override fun onDestroy() {
        presenter?.dropView()
        super.onDestroy()
    }

    override fun showError(error: String) {
    }

    override fun mainActivity() {
        Intent(this, MyHomeActivity::class.java).let {
//            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(it)
        }
    }
}
