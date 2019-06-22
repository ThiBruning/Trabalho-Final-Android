package com.ifpr.bruning.posts.ui.items

import android.view.View
import com.ifpr.bruning.posts.models.Post
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import com.ifpr.bruning.posts.R
import com.ifpr.bruning.posts.listener.PostListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.post_item.view.*

class PostItem(val post: Post, val of_author: Boolean, val listener: PostListener?): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.post_item
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.txt_title.text = post.title
        viewHolder.itemView.txt_author.text = post.authorName
        val target = viewHolder.itemView.image_post
        Picasso.get().load(post.image).into(target)
        if (of_author) {
            viewHolder.itemView.bt_post_delete.visibility = View.VISIBLE
            viewHolder.itemView.bt_post_delete.setOnClickListener {
                val position = viewHolder.position
//                val position = it.get
                listener?.removePost(post, position)
            }
        }

    }
}