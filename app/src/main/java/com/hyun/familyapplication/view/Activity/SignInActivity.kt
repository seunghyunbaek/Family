package com.hyun.familyapplication.view.Activity

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.SignInContract
import com.hyun.familyapplication.presenter.SignInPresenter
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.android.synthetic.main.activity_sign_in.*
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder


class SignInActivity : BaseActivity(), SignInContract.View, View.OnClickListener {

    private lateinit var mPresenter: SignInPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mPresenter.takeView(this)

        AndroidThreeTen.init(this)

        button_signin_with_google.setOnClickListener(this)
        button_signin_with_kakao.setOnClickListener(this)
    }

    public override fun onStart() {
        super.onStart()
        if (mPresenter != null)
            initPresenter()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.dropView()
    }

    override fun initPresenter() {
        mPresenter = SignInPresenter()
    }

    override fun showError(error: String) {
        Toast.makeText(this@SignInActivity, error, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progress_signin.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_signin.visibility = View.GONE
    }

    override fun successSignIn() {
        Intent(this, MainActivity::class.java).let {
            startActivity(it)
        }
    }

    private fun signIn() {
        val signInIntent: Intent = mPresenter.initGoogleSignIn(
            this@SignInActivity,
            getString(R.string.firebase_server_key)
        )
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onClick(v: View) {
        val i = v.id

        when (i) {
            R.id.button_signin_with_google -> signIn()
            R.id.button_signin_with_kakao -> {
                Intent(this, MainActivity::class.java).let {
                    startActivity(it)
                }
            }
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        mPresenter.result(requestCode, resultCode, data)
    }

    companion object {
        private const val TAG = "SignInActiity"
        private const val RC_SIGN_IN = 9001
        private const val TABLE_NAME = "User"

        class sendAsyncTask : AsyncTask<String, Any, String?>() {
            override fun doInBackground(vararg params: String?): String? {

                val url = params[0]
                val email = params[1]
                val name = params[2]
                val hoching = params[3]
                val gender = params[4]
                val phone = params[5]
                val anniversary = params[6]

                var reqParam =
                    URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
                reqParam += "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(
                    name,
                    "UTF-8"
                )
                reqParam += "&" + URLEncoder.encode("hoching", "UTF-8") + "=" + URLEncoder.encode(
                    hoching,
                    "UTF-8"
                )
                reqParam += "&" + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(
                    gender,
                    "UTF-8"
                )
                reqParam += "&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(
                    phone,
                    "UTF-8"
                )
                reqParam += "&" + URLEncoder.encode(
                    "anniversary",
                    "UTF-8"
                ) + "=" + URLEncoder.encode(anniversary, "UTF-8")

                val mURL = URL(url)

                with(mURL.openConnection() as HttpURLConnection) {
                    requestMethod = "POST"

                    val wr = OutputStreamWriter(outputStream)
                    wr.write(reqParam)
                    wr.flush()

                    println("URL : $url")
                    println("Response Code : $responseCode")
                    println("d $responseMessage")

                }
                return null
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                println(result)
            }
        }
    }
}
