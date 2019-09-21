package me.doapps.core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.doapps.core.model.Document
import me.doapps.core.retrofit.RetrofitConfig.coreApi
import me.doapps.core.session.Preferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkingInfoViewModel : ViewModel(), Callback<List<Document>> {

    val documentList = MutableLiveData<List<Document>>()
    val toastMessage = MutableLiveData<String>()

    lateinit var preferences: Preferences

    fun loadDocuments() {

        coreApi.getDocumentListByUserId(preferences.userId).enqueue(this)

    }

    override fun onResponse(call: Call<List<Document>>, response: Response<List<Document>>) {
        when (response.code()) {
            200 -> documentList.value = response.body()
            404 -> toastMessage.value = "No se encontraron documentos para este usuario"
        }

    }

    override fun onFailure(call: Call<List<Document>>, t: Throwable) {
        toastMessage.value = "Algo ocurrio mal"
    }

}