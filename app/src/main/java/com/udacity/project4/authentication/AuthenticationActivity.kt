package com.udacity.project4.authentication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.udacity.project4.R
import com.udacity.project4.databinding.ActivityAuthenticationBinding
import com.udacity.project4.locationreminders.RemindersActivity

/**
 * This class should be the starting point of the app, It asks the users to sign in / register, and redirects the
 * signed in users to the RemindersActivity.
 */
class AuthenticationActivity : AppCompatActivity() {

    lateinit var binding: ActivityAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

//         TODO: Implement the create account and sign in using FirebaseUI, use sign in using email and sign in using Google

//         TODO: If the user was authenticated, send him to RemindersActivity

//         TODO: a bonus is to customize the sign in flow to look nice using :


        binding.loginBtb.setOnClickListener {
            startSinInFlow()
        }
    }

    private fun startSinInFlow() {

        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()


        )
        val firebaseIntent =
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).setLogo(R.drawable.map)
                .build()

        getResult.launch(firebaseIntent)
    }

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val response = IdpResponse.fromResultIntent(it.data)
        if (it.resultCode == RESULT_OK){
            Log.i(TAG, "Successfully signed in user ${FirebaseAuth.getInstance().currentUser?.displayName}!")
            backToRemindersActivity()
            Toast.makeText(this,"Welcome ${FirebaseAuth.getInstance().currentUser?.displayName}",Toast.LENGTH_LONG).show()

        }else{
            Log.i(TAG, "Sign in unsuccessful ${response?.error?.errorCode}")
            Toast.makeText(this,"Login Unsuccessful please try Again",Toast.LENGTH_LONG).show()
        }
    }

    private fun backToRemindersActivity(){
        val intent = Intent(this,RemindersActivity::class.java)
        startActivity(intent)
    }

}
