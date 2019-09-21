package me.doapps.core.viewmodel

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import me.doapps.core.utils.logInfo
import me.doapps.core.model.User
import me.doapps.core.retrofit.RetrofitConfig.coreApi
import me.doapps.core.session.Preferences
import me.doapps.core.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    val openSignInDialog = MutableLiveData<Intent>()
    val loginSuccess = MutableLiveData<Boolean>()
    val toastMessage = MutableLiveData<String>()

    lateinit var preferences: Preferences
    lateinit var signInClient: GoogleSignInClient


    private fun login(email: String) {
        coreApi.doLoginRequest(email).enqueue(object : Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {

                when (response.code()) {

                    200 -> response.body()?.let {
                        preferences.registerUser(it)
                        toastMessage.value = "Bienvenido ${it.name} ${it.lastname}"
                        loginSuccess.value = true
                    }
                    400 -> {
                        toastMessage.value =
                            "El email $email no está registrado"// To login with another account
                        resetLogin()

                    }
                    else -> {
                        toastMessage.value = "Error ${response.code()}"
                        resetLogin()
                    }
                }

            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                toastMessage.value = "Algo ocurrió mal"
                resetLogin()
            }

        })
    }

    fun checkLogin() {
        if (preferences.userId != -1) {
            logInfo("Usuario logueado con el id ${preferences.userId}")
            loginSuccess.value = true
        } else {
            logInfo("Usuario no logueado gaaaa")
        }
    }

    fun openGoogleSignInClic() {
        openSignInDialog.value = signInClient.signInIntent
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.requestCodeGoogleSignIn) {
            logInfo("A ber $resultCode")
            if (resultCode == Activity.RESULT_OK) {
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                logInfo("Email a logear ${task.result!!.email.toString()}")
                login(task.result!!.email.toString())
            }


        }


    }

    fun resetLogin() {
        signInClient.revokeAccess()
        signInClient.signOut()
    }


}