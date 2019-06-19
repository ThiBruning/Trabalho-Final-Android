package com.ifpr.bruning.posts.models

import com.google.gson.annotations.SerializedName

class Post(
    @SerializedName("author_id")
    var author_id: Long,
    @SerializedName("authorName")
    var authorName: String?,
    @SerializedName("title")
    var title: String,
    @SerializedName("body")
    var body: String,
    @SerializedName("image")
    var image: String
) {
    var id: Long = 0
    var author:Author? = null
}