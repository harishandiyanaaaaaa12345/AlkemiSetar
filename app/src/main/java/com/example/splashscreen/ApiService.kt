package com.example.splashscreen

import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @FormUrlEncoded
    @POST("QR/register")
    fun postRegister(

        @Field("nim") nim: String,
        @Field("nama") nama: String,
        @Field("email") email: String = "emai.com",
        @Field("prodi") prodi: String = "ifd3",
        @Field("password") password: String
    ): Call<Response>

    @GET("QR/login")
    fun getLogin(
        @Query("nim") id: String,
        @Query("password") password: String
    ): Call<Response>

    @FormUrlEncoded
    @POST("QR/token")
    fun postScan(
        @Field("nim") nim: String,
        @Field("token") token: String
    ): Call<Response>
}