package com.ifpr.bruning.posts.listener

import com.ifpr.bruning.posts.models.Post

interface PostListener {
    fun removePost(post: Post)
}