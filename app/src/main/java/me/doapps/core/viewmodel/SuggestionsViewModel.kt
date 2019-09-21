package me.doapps.core.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.doapps.core.extension.logInfo
import me.doapps.core.model.MessageResponse
import me.doapps.core.retrofit.RetrofitConfig.coreApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SuggestionsViewModel : ViewModel() {

    val sendSuccess = MutableLiveData<Boolean>()
    val toastMessage = MutableLiveData<String>()

    fun sendSuggestion(suggestion: String) {

        if(suggestion.trim().isEmpty()){
            toastMessage.value = "Debes ingresar una sugerencia válida"
            return
        }

        coreApi.sendSuggestion(suggestion).enqueue(object : Callback<MessageResponse> {
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {

                when (response.code()) {

                    200 -> {
                        sendSuccess.value = true
                        toastMessage.value = response.body()?.message
                    }
                    else -> {
                        toastMessage.value = "Error ${response.code()}"
                    }


                }

            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                toastMessage.value = "Algo ocurrió mal"
            }


        })

    }

}