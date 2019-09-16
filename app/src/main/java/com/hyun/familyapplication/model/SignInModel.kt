package com.hyun.familyapplication.model

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hyun.familyapplication.Log
import com.hyun.familyapplication.contract.SignInContract

class SignInModel : Log {

    private val RC_SIGN_IN = 9001
    private val TAG = "SignInModel"
    private var mOnListener: SignInContract.onSignInListener

    constructor(signInLister: SignInContract.onSignInListener) {
        mOnListener = signInLister
    }

    fun initGoogleSignIn(context: Context, key: String): Intent {

        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(key)
                .requestEmail()
                .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(context, gso)

        val signinIntent: Intent = mGoogleSignInClient.signInIntent

        return signinIntent
    }

    fun result(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RC_SIGN_IN) {
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account!!)
                } catch (e: ApiException) {
                    Log._E(TAG, e.toString())
                }
            }
        }
    }

    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        val mAuth = FirebaseAuth.getInstance()

        mAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                mAuth.currentUser
                mOnListener.onSuccess()
            }
        }
    }
}