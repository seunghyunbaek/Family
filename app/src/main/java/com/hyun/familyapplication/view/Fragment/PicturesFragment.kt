package com.hyun.familyapplication.view.Fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.PictureContract
import com.hyun.familyapplication.model.RecordImage
import com.hyun.familyapplication.presenter.PicturePresenter
import com.hyun.familyapplication.view.Adapter.PictureAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PicturesFragment : BaseFragment(), PictureContract.View {

    private lateinit var mPresenter: PictureContract.Presenter

    private lateinit var pictureRecyclerView: RecyclerView
    private var pictureAdapter: PictureAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_pictures, container, false)

        mPresenter.takeView(this)

        pictureRecyclerView = view.findViewById(R.id.recyclerview_picture)
        pictureRecyclerView.layoutManager = GridLayoutManager(view.context, 4)
        pictureAdapter = PictureAdapter(context!!)
        mPresenter.takeAdapter(pictureAdapter!!)
        pictureRecyclerView.adapter = pictureAdapter

        return view
    }

    override fun onStart() {
        super.onStart()
        if (mPresenter == null)
            initPresenter()

        refreshData()
    }

    override fun onDestroy() {
        mPresenter.dropView()
        super.onDestroy()
    }

    override fun initPresenter() {
        mPresenter = PicturePresenter()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(error: String) {
    }

    override fun showPictures(list:ArrayList<RecordImage>) {
    }

    fun refreshData() {
        mPresenter.getPictures(context!!)
    }
}
