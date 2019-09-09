package com.hyun.familyapplication.view.Activity

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.SignInContract
import com.hyun.familyapplication.model.User
import com.hyun.familyapplication.presenter.SignInPresenter
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.android.synthetic.main.activity_sign_in.*
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder


class SignInActivity : BaseActivity(), SignInContract.View, View.OnClickListener {

    private lateinit var mPresenter: SignInPresenter

    // [START declare Auth]
    private lateinit var mAuth: FirebaseAuth
    // [END declare_auth]

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private lateinit var db: DBHelper

    lateinit var stringUrl :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mPresenter.takeView(this)
        db = DBHelper(this, TABLE_NAME)

        stringUrl = getString(R.string.url)

        AndroidThreeTen.init(this)

        // Button Listeners
        button_signin_with_google.setOnClickListener(this)
        button_signin_with_kakao.setOnClickListener(this)

        // [START config_signin]
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("533109401813-5h2qjgig0q4d2pkn8g0c3af6l798imk7.apps.googleusercontent.com")
            .requestEmail()
            .build()
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        // [START initialize_auth
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        // [END initialize_auth]
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.dropView()
    }

    fun setButton() {
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


    // [START on_start_check_user]
    public override fun onStart() {
        super.onStart()

        //Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
//        updateUI(currentUser)
    }
    // [END on_start_check_user]

    // [START onactivityresult]
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returend from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
//                val account = task.result
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // [START_EXCLUDE]
                updateUI(null)
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]


    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGOogle:" + acct.id!!)
        // [START_EXCLUDE silent]
//        showProgressDialog()
//        showLoading()
        // [END_EXCLUDE]

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, diplay a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }

                // [START_EXCLUDE]
//                hideProgressDialog()
//                hideLoading()
                // [END_EXCLUDE]
            }
    }
    // [END auth_with_google]

    // [START signin]
    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    // [END signin]

    private fun signOut() {
        // Firebase sign out
        mAuth.signOut()

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this) {
            updateUI(null)
        }
    }

    private fun updateUI(user: FirebaseUser?) {
//        hideProgressDialog()
//        hideLoading()

        if (user != null) {
            // 장고에 저장하기
            val djangoUser = User()
            djangoUser.email = user.email.toString()
            djangoUser.name = user.displayName.toString()

            sendAsyncTask().execute(stringUrl + "family/signin/",
                djangoUser.email,
                djangoUser.name,
                djangoUser.hoching,
                djangoUser.gender,
                djangoUser.phone,
                djangoUser.anniversary
                )

            val newUser = user.email?.let { db.getUser(it) }
            if (newUser == null) {
                val addUser = User()
                addUser.email = user.email.toString()
                addUser.name = user.displayName.toString()
                db.addUser(addUser)
            }
            Intent(this, MainActivity::class.java).let {
                startActivity(it)
            }
        } else {
//            btnSignInWithFacebook.text = null
//            btnSignInWithKakao.text = null
        }

    }

    override fun onClick(v: View) {
        val i = v.id

        when (i) {
            R.id.button_signin_with_google -> signIn()
            R.id.button_signin_with_kakao -> {
//                mPresenter.doLogin("a1@gmail.com")
                Intent(this, MainActivity::class.java).let {
                    startActivity(it)
                }
//                var tmpMail = "a01093705783@gmail.com"
//                tmpMail = tmpMail.replace("@", "_")
//                val user = db.getUser(tmpMail)
//                if (user != null) {
//                    Log.d("jkljkljkl", "유저데이터 있음:" + user.email)
//                    Toast.makeText(this@SignInActivity, user.email, Toast.LENGTH_SHORT).show()
//                } else {
//                    Log.d("jkljkljkl", "유저데이터 없음")
//                }
            }
        }
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

                var reqParam = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
                reqParam += "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")
                reqParam += "&" + URLEncoder.encode("hoching", "UTF-8") + "=" + URLEncoder.encode(hoching, "UTF-8")
                reqParam += "&" + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8")
                reqParam += "&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8")
                reqParam += "&" + URLEncoder.encode("anniversary", "UTF-8") + "=" + URLEncoder.encode(anniversary, "UTF-8")

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
