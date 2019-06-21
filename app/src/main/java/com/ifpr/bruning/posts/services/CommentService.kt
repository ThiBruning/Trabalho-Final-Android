package com.ifpr.bruning.posts.services

import com.ifpr.bruning.posts.models.Comment
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface CommentService {
    @Headers("Accept: application/json")
    @POST("comment")
    @FormUrlEncoded
    fun insert(@Field("post_id") post_id: Long,
               @Field("author_id") author_id: Long,
               @Field("body") body: String): Call<Comment>
}