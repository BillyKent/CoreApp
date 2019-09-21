package me.doapps.core.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import me.doapps.core.R
import me.doapps.core.databinding.ActivityLoginBinding
import me.doapps.core.extension.getGoogleSignInClientFromContext
import me.doapps.core.extension.openAndFinishActivity
import me.doapps.core.extension.showLongToast
import me.doapps.core.session.Preferences
import me.doapps.core.utils.Constants
import me.doapps.core.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var viewmodel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewmodel.preferences = Preferences.getInstance(this)
        viewmodel.checkLogin() // check the session first
        viewmodel.signInClient = getGoogleSignInClientFromContext()!!

        // binding configuration
        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewmodel = viewmodel
        binding.executePendingBindings()

        observeViewModel()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewmodel.onActivityResult(requestCode, resultCode, data) // delegates handle to viewmodel
    }

    private fun observeViewModel() {

        viewmodel.openSignInDialog.observe(this, Observer {
            startActivityForResult(it, Constants.requestCodeGoogleSignIn)
        })


        viewmodel.toastMessage.observe(this, Observer {
            showLongToast(message = it)
        })

        viewmodel.loginSuccess.observe(this, Observer {
                openAndFinishActivity(HomeActivity::class.java)
        })

    }

}
