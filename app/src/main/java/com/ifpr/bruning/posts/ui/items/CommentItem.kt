package com.ifpr.bruning.posts.ui.items

import com.ifpr.bruning.posts.R
import com.ifpr.bruning.posts.models.Comment
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.comment_item.view.*

class CommentItem(val comment: Comment) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.comment_item
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.txt_comment_body.text = comment.body
        viewHolder.itemView.txt_comment_author.text = comment.authorName
    }
}
