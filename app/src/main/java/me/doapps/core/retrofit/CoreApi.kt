package me.doapps.core.retrofit

import me.doapps.core.model.Benefit
import me.doapps.core.model.Document
import me.doapps.core.model.MessageResponse
import me.doapps.core.model.User
import retrofit2.Call
import retrofit2.http.*

interface CoreApi {

    @POST("auth/signin")
    @FormUrlEncoded
    fun doLoginRequest(@Field("email") email: String): Call<User>

    @GET("benefit")
    fun getEntertainmentBenefits(): Call<List<Benefit>>

    @GET("tool/{id}")
    fun getToolsByUserId(@Path("id") userId: Int): Call<List<Benefit>>

    @POST("suggestion")
    @FormUrlEncoded
    fun sendSuggestion(@Field("message") suggestion: String): Call<MessageResponse>

    @POST("attendance/register")
    @FormUrlEncoded
    fun markAssistance(@Field("employee_id") userId: Int): Call<MessageResponse>

    @GET("doc/{id}")
    fun getDocumentListByUserId(@Path("id") userId: Int): Call<List<Document>>

}