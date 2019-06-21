package com.ifpr.bruning.posts.services

import com.ifpr.bruning.posts.models.Post
import retrofit2.Call
import retrofit2.http.*

interface PostService {

    @Headers("Accept: application/json")
    @GET("posts")
    fun allPosts() : Call<List<Post>>

    @Headers("Accept: application/json")
    @POST("post")
    @FormUrlEncoded
    fun insert(@Field("author_id") id: Long,
               @Field("title") title: String,
               @Field("image") image: String,
               @Field("body") body: String): Call<Post>

    @Headers("Accept: application/json")
    @GET("post/{id}")
    fun show(@Path("id") id: Long) : Call<Post>

    @Headers("Accept: application/json")
    @GET("author/{id}/posts")
    fun authorPosts(@Path("id") id: Long) : Call<List<Post>>
}