package siapasaya.test.sehatq.view.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.fragment_sign_in.*
import siapasaya.test.sehatq.R
import siapasaya.test.sehatq.utils.BaseFragment
import siapasaya.test.sehatq.viewmodel.main.MainViewModel


/**
 * Created by siapaSAYA on 7/28/2020
 */

class FragmentSignIn : BaseFragment() {

    override fun layoutResId(): Int = R.layout.fragment_sign_in
    val RC_SIGN_IN = 200
    val callbackManager = CallbackManager.Factory.create()

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        abc_action_sign_in.setOnClickListener {
            login()
        }
        abc_action_google.setOnClickListener {
            val gso =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
            val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
            mGoogleSignInClient.revokeAccess().addOnCompleteListener {}
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        abc_action_facebook.setReadPermissions("email")
        abc_action_facebook.fragment = this
        abc_action_facebook.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                login()
            }

            override fun onCancel() {
                // App code
            }

            override fun onError(exception: FacebookException) {
                // App code
            }
        })
        abc_action_facebook.setOnClickListener {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)
                login()
        } catch (e: ApiException) {

        }
    }

    fun login(){
        val direction =
            FragmentSignInDirections.actionFragmentSIGNINToFragmentMAIN()
        findNavController().navigate(direction)
    }
}