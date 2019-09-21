package me.doapps.core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import me.doapps.core.session.Preferences

class HomeViewModel : ViewModel() {

    lateinit var preferences: Preferences
    lateinit var signInClient: GoogleSignInClient

    enum class Destination(val destinationName: String) {
        DEV_TOOLS("Herramientas"),
        ENTERTAINMENT("Entretenimiento"),
        WORKING_INFO("Laboral"),
        SUGGESTIONS("Sugerencias"),
        ASSISTANCE("Asistencia"),
        LOGOUT("Cerrar sesion")
    }

    val destiny: MutableLiveData<Destination> = MutableLiveData()


    val menuItems = listOf(
        Destination.DEV_TOOLS,
        Destination.ENTERTAINMENT,
        Destination.WORKING_INFO,
        Destination.SUGGESTIONS
    )

    fun logout() {
        preferences.unregisterUser()
        signInClient.revokeAccess()
        signInClient.signOut()
        destiny.value = Destination.LOGOUT
    }

    fun assistance (){
        destiny.value = Destination.ASSISTANCE
    }


    fun chooseDestination(destination: Destination) {
        destiny.value = destination
    }
}