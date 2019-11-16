package com.hyun.familyapplication.presenter

import android.content.Context
import com.hyun.familyapplication.contract.InvitedContract
import com.hyun.familyapplication.model.Invite
import com.hyun.familyapplication.model.InvitedModel
import com.hyun.familyapplication.view.Adapter.InvitedAdapter
import org.json.JSONArray

class InvitedPresenter: InvitedContract.Presenter, InvitedContract.Listener {
    var view:InvitedContract.View? = null
    var adapte:InvitedAdapter? = null

    override fun setAdapter(adapte: InvitedAdapter) {
        this.adapte = adapte
        adapte.setListener(this)
    }

    override fun getInvited(url:String, context: Context) {
        InvitedModel.getInvited(url, context, this)
    }

    override fun takeView(view: InvitedContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun onSuccess(result: String) {
        val jsonArray = JSONArray(result)
        val list = ArrayList<Invite>()
        var cnt = 0

        while(cnt < jsonArray.length()) {
            val json = jsonArray.getJSONObject(cnt)
            val invite = Invite()
            invite.id = json.getInt("id")
            invite.inviter = json.getString("inviter")
            invite.guest = json.getString("guest")
            invite.room = json.getInt("room")
            list.add(invite)
            cnt++
        }

        adapte?.setData(list)
        adapte?.notifyDataSetChanged()
    }

    override fun onPositive(context:Context, invite:Invite) {
        InvitedModel.putPositive(context, invite, this)
    }

    override fun onPutEnd(context: Context, result:String) {
        val result = InvitedModel.updateUser(context, result)
        if(result != -1) {
            view?.mainActivity()
        }
    }
}