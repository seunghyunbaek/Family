package com.hyun.myapplication.view.Activity

import android.content.Intent
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
import com.hyun.myapplication.R
import com.hyun.myapplication.contract.SignInContract
import com.hyun.myapplication.presenter.SignInPresenter
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : BaseActivity(), SignInContract.View, View.OnClickListener {

    private lateinit var mPresenter: SignInPresenter

    // [START declare Auth]
    private lateinit var mAuth: FirebaseAuth
    // [END declare_auth]

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mPresenter.takeView(this)


        AndroidThreeTen.init(this)

        // Button Listeners
        button_signin_with_google.setOnClickListener(this)
        button_signin_with_kakao.setOnClickListener(this)

        // [START config_signin]
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("533109401813-op8ntr8hvjtuhammq5267usbv4ttdtdi.apps.googleusercontent.com")
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
//        btnSignInWithGoogle.setOnClickListener {
//            if (mPresenter.doLogin("a1@gmail.com", "123")) {
//                Intent(this, MainActivity::class.java).let {
//                    startActivity(it)
//                }
//            }
//            mPresenter.doLogin("a1@gmail.com")
//        }

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
        updateUI(currentUser)
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
//            btnSignInWithFacebook.text = user.email
//            btnSignInWithKakao.text = user.uid
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
                mPresenter.doLogin("a1@gmail.com")
            }
        }
    }

    companion object {
        private const val TAG = "SignInActiity"
        private const val RC_SIGN_IN = 9001
    }
}
