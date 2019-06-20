package com.ifpr.bruning.posts.models

import com.google.gson.annotations.SerializedName

class Comment(
    @SerializedName("body")
    var body: String,
    @SerializedName("post_id")
    var post_id: Long
)