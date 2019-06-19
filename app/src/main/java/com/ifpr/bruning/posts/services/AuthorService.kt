package com.ifpr.bruning.posts.services

import com.ifpr.bruning.posts.models.Author
import retrofit2.Call
import retrofit2.http.*

interface AuthorService {
    @Headers("Accept: application/json")
    @GET("author/{id}")
    fun find(@Path("id") id: Long) : Call<Author>

    @Headers("Accept: application/json")
    @POST("login")
    @FormUrlEncoded
    fun login(@Field("email") email: String,
              @Field("password") password: String): Call<Author>

    @Headers("Accept: application/json")
    @POST("register")
    @FormUrlEncoded
    fun insert(@Field("name") name: String,
               @Field("email") email: String,
               @Field("password") password: String): Call<Author>
}