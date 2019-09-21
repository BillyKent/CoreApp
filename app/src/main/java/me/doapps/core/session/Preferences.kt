package me.doapps.core.session

import android.content.Context
import android.content.SharedPreferences
import me.doapps.core.model.User

object Preferences {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private const val preferenceApplicationName = "core_app"
    private const val preferenceUserId = "user_id"
    private const val preferenceUserName = "user_name"
    private const val preferenceUserLastName = "user_lastname"
    private const val preferenceUserEmail = "user_email"
    private const val preferenceQrCode = "user_qr_code"

    fun getInstance(context: Context): Preferences {
        sharedPreferences =
            context.getSharedPreferences(preferenceApplicationName, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        return this
    }

    var userId: Int
        get() = sharedPreferences.getInt(preferenceUserId, -1)
        set(value) {
            editor.putInt(preferenceUserId, value).commit()
        }

    var userName: String?
        get() = sharedPreferences.getString(preferenceUserName, "")
        set(value) {
            editor.putString(preferenceUserName, value).commit()
        }
    var userLastName: String?
        get() = sharedPreferences.getString(preferenceUserLastName, "")
        set(value) {
            editor.putString(preferenceUserLastName, value).commit()
        }

    var userEmail: String?
        get() = sharedPreferences.getString(preferenceUserEmail, "")
        set(value) {
            editor.putString(preferenceUserEmail, value).commit()
        }

    var qrCode: String?
        get() = sharedPreferences.getString(preferenceQrCode, "")
        set(value) {
            editor.putString(preferenceQrCode, value).commit()
        }

    fun registerUser(user: User) {
        userId = user.id
        userName = user.name
        userLastName = user.lastname
        userEmail = user.email
        qrCode = user.qrCode
    }

    fun unregisterUser() {
        userId = -1
        userName = ""
        userLastName = ""
        userEmail = ""
        qrCode = ""
    }


}