package me.doapps.core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import me.doapps.core.model.MessageResponse
import me.doapps.core.retrofit.RetrofitConfig.coreApi
import me.doapps.core.session.Preferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssistanceViewModel : ViewModel(), ZXingScannerView.ResultHandler {

    val toastMessage = MutableLiveData<String>()
    val assistanceSuccess = MutableLiveData<Boolean>()

    lateinit var preferences: Preferences

    override fun handleResult(rawResult: Result?) {
        if (rawResult.toString() == preferences.qrCode) {
            markAssistance()
        } else {
            toastMessage.value = "Código QR no válido"
            assistanceSuccess.value = false
        }

    }

    private fun markAssistance() {

        coreApi.markAssistance(preferences.userId).enqueue(object : Callback<MessageResponse> {

            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                when (response.code()) {
                    200 -> {
                        toastMessage.value = response.body()?.message
                        assistanceSuccess.value = true
                    }
                    else -> toastMessage.value = "Error ${response.code()}"

                }

            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                toastMessage.value = "Algo salió mal"
            }


        })
    }

}