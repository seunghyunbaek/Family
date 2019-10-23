package com.hyun.familyapplication.model

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.Log
import com.hyun.familyapplication.R
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
                // 비동기처리로인한 리턴 시점이 맞지않아 리스너 선택
                mAuth.currentUser?.let {
                    mOnListener.onSuccess(it.email.toString(), it.displayName.toString())
                }
            }
        }
    }

    fun getUser(context: Context):User? {
        val dbHelper = DBHelper(context)
        return dbHelper.getUser()
    }

    fun getUserfromServer(context: Context, email:String, name:String) {
        val url = context.getString(R.string.url) + "user/$email/"
        APIUtils.getSignInAsyncTask(mOnListener, context, email, name).execute(url)
    }

    fun saveUser(context: Context, email: String, name: String) {

        // 서버에 저장하기
        val cv = ContentValues()
        cv.put("email", email.replace(".", "_"))
        cv.put("name", name)
//        cv.put("email", "asd@gmail_com")
//        cv.put("name", "asd")
        val data = APIUtils.makeJson(cv)

        val url = context.getString(R.string.url) + "user/"
        println("--------------------------------------------------------")
        println(url)
        println(data)
        println("--------------------------------------------------------")
//        APIUtils.getAsyncTask().execute(url)
        APIUtils.postSignInAsyncTask(mOnListener, context, email, name).execute(url, data)

//        APIUtils.getAsyncTask(mOnListener).execute(url)
    }



    fun saveUserSQLite(context: Context, email: String, name: String) {
        // 내부 저장하기
        val dbHelper = DBHelper(context)
//        dbHelper.deleteRoom()

        val user = User()
        user.email = email.replace(".", "_")
        user.name = name

//        val user2 = dbHelper.getUser()

//        if (dbHelper.getUser() != null)
//            dbHelper.deleteUser()

        if (dbHelper.getUser() == null) {
            dbHelper.addUser(user)
            println("-------------------------------------------------------------------------")
            println("-----------------         saveUserSQLite(유저 없음)        -------------------")
            println("-------------------------------------------------------------------------")
        } else {
            val user = dbHelper.getUser()
            if(user?.email != email) {
                dbHelper.deleteUser()
                val newUser = User()
                newUser.email = email
                newUser.name = name
                dbHelper.addUser(newUser)
            }
            println("-------------------------------------------------------------------------")
            println("-----------------         saveUserSQLite(유저 존재)        -------------------")
            println("-------------------------------------------------------------------------")
        }
    }

    fun saveUserSQLite(context: Context, email: String, name: String, room:Int) {
        // 내부 저장하기
        val dbHelper = DBHelper(context)
//        dbHelper.deleteRoom()

        val user = User()
        user.email = email.replace(".", "_")
        user.name = name
        user.room = room

//        val user2 = dbHelper.getUser()

//        if (dbHelper.getUser() != null)
//            dbHelper.deleteUser()

        if (dbHelper.getUser() == null) {
            dbHelper.addUser(user)
            println("-------------------------------------------------------------------------")
            println("-----------------         saveUserSQLite(유저 없음)        -------------------")
            println("-------------------------------------------------------------------------")
        } else {
            val user = dbHelper.getUser()
            if(user?.email != email) {
                dbHelper.deleteUser()
                val newUser = User()
                newUser.email = email
                newUser.name = name
                dbHelper.addUser(newUser)
            }
            println("-------------------------------------------------------------------------")
            println("-----------------         saveUserSQLite(유저 존재)        -------------------")
            println("-------------------------------------------------------------------------")
        }
    }

}