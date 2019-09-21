package me.doapps.core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.doapps.core.extension.logInfo
import me.doapps.core.model.Benefit
import me.doapps.core.retrofit.RetrofitConfig.coreApi
import me.doapps.core.session.Preferences
import me.doapps.core.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BenefitsViewModel : ViewModel() {

    lateinit var preferences: Preferences

    val benefits = MutableLiveData<List<Benefit>>()


    fun loadBenefits(benefitType: Int) {
        when (benefitType) {
            Constants.entertainmentBenefits -> loadEntertainmentBenefits()
            Constants.toolsBenefits -> loadTools()
        }
    }

    private fun loadTools() {
        coreApi.getToolsByUserId(preferences.userId).enqueue(object : Callback<List<Benefit>> {

            override fun onResponse(call: Call<List<Benefit>>, response: Response<List<Benefit>>) {
                when (response.code()) {
                    200 -> benefits.value = response.body()
                }
            }
            override fun onFailure(call: Call<List<Benefit>>, t: Throwable) {

            }
        })

    }

    private fun loadEntertainmentBenefits() {
        coreApi.getEntertainmentBenefits().enqueue(object : Callback<List<Benefit>> {

            override fun onResponse(call: Call<List<Benefit>>, response: Response<List<Benefit>>) {
                when (response.code()) {
                    200 -> benefits.value = response.body()

                }

            }

            override fun onFailure(call: Call<List<Benefit>>, t: Throwable) {

            }
        })

    }


}