package com.ifpr.bruning.posts.models

import com.google.gson.annotations.SerializedName

class Author(
    @SerializedName("name")
    var name:String,
    @SerializedName("email")
    var email:String,
    @SerializedName("password")
    var password: String
) { var id: Long = 0 }