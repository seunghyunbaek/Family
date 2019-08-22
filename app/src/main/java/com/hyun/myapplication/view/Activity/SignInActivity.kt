package com.hyun.myapplication.view.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hyun.myapplication.R
import com.hyun.myapplication.contract.SignInContract
import com.hyun.myapplication.presenter.SignInPresenter
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : BaseActivity(), SignInContract.View {

    private lateinit var signinPresenter: SignInPresenter
    // Google Login result
    private val RC_SIGN_IN: Int = 1

    // Google Api Client
    private var googleSignInClient: GoogleSignInClient? = null

    // Firebase Auth
    private var firebaseAuth: FirebaseAuth? = null

    private var mGoogleApiClient: GoogleApiClient? = null

    private var mAuth:FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // [START config_signin]
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("533109401813-op8ntr8hvjtuhammq5267usbv4ttdtdi.apps.googleusercontent.com")
            .requestEmail()
            .build()

//        firebaseAuth = FirebaseAuth.getInstance()
        mAuth = FirebaseAuth.getInstance()


        signinPresenter.takeView(this)

        setGoogleLogin()
        setButton()
//        setupUI()

        AndroidThreeTen.init(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        signinPresenter.dropView()
    }

    fun setButton() {
//        btnSignInWithGoogle.setOnClickListener {
//            if (signinPresenter.doLogin("a1@gmail.com", "123")) {
//                Intent(this, MainActivity::class.java).let {
//                    startActivity(it)
//                }
//            }
//            signinPresenter.doLogin("a1@gmail.com")
//        }

        btnSignInWithGoogle.setOnClickListener {
//            val signInIntent:Intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
//            startActivityForResult(signInIntent, RC_SIGN_IN)
            loginGoogle()
        }
    }

    override fun initPresenter() {
        signinPresenter = SignInPresenter()
    }

    override fun showError(error: String) {
        Toast.makeText(this@SignInActivity, error, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressSignIn.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressSignIn.visibility = View.GONE
    }

    override fun successSignIn() {
        Intent(this, MainActivity::class.java).let {
            startActivity(it)
        }
    }

    private fun setGoogleLogin() {
        FirebaseAuth.getInstance().signOut()

        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("533109401813-op8ntr8hvjtuhammq5267usbv4ttdtdi.apps.googleusercontent.com")
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(
                this /* FragmentActivity */
                /* OnConnectionFailedListener */
            ) { }
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
    }

    private fun loginGoogle() {
        val signInIntent: Intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val result: GoogleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data)

            if (result != null) {
                if (result.isSuccess) {
                    // 성공시
                    val acct: GoogleSignInAccount? = result.signInAccount

                    val personName: String = acct?.displayName.toString()
                    val personEmail: String = acct?.email.toString()
                    val personId: String = acct?.id.toString()
                    val tokenKey: String = acct?.serverAuthCode.toString()

                    Toast.makeText(this, personName, Toast.LENGTH_SHORT).show()
                    mGoogleApiClient?.disconnect()
                } else {
                    // 실패
                    Toast.makeText(this, "실패" + result.status.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        } else {

        }
    }

    private fun firebaseAuthGoogle(acct: GoogleSignInAccount) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(acct.idToken, null)

        mAuth?.signInWithCredential(credential)?.addOnCompleteListener(this, {
            if(!it.isSuccessful) {
                Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
            }
        })

    }

}
