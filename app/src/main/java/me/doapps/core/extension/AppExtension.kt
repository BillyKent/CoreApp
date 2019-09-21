package me.doapps.core.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import me.doapps.core.R


fun Context.showLongToast(
    context: Context = applicationContext,
    message: String,
    duration: Int = Toast.LENGTH_LONG
) = Toast.makeText(context, message, duration).show()

fun Context.showShortToast(
    context: Context = applicationContext,
    message: String,
    duration: Int = Toast.LENGTH_SHORT
) = Toast.makeText(context, message, duration).show()

fun Context.getGoogleSignInClientFromContext(): GoogleSignInClient? {
    val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build()
    return GoogleSignIn.getClient(this, signInOptions)
}

fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

fun <T> Activity.openAndFinishActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
    finish()
}

fun Context.checkPermission(permission: String): Boolean =
    (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)


fun AppCompatActivity.setToolbarWithBackButton(toolbarTitle: String, toolbar: Toolbar) {
    setSupportActionBar(toolbar)
    supportActionBar!!.apply {
        setDisplayHomeAsUpEnabled(true)
        setHomeAsUpIndicator(R.drawable.ic_return)
        title = toolbarTitle
    }
    toolbar.setNavigationOnClickListener(View.OnClickListener { (this as ComponentActivity).onBackPressed() })
}


fun Any.logInfo(message: String) = Log.i(this::class.java.simpleName, message)

