package com.chanplusplus.fakeapiposts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chanplusplus.fakeapiposts.R
import com.chanplusplus.fakeapiposts.models.Comment

class PostCommentsAdapter(private val comments: List<Comment>) : RecyclerView.Adapter<PostCommentsAdapter.PostCommentViewHolder>() {

    class PostCommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewBody: TextView = view.findViewById(R.id.textview_element_post_comments_body)
        val textViewCommenter: TextView = view.findViewById(R.id.textview_element_post_comments_commenter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostCommentViewHolder {
        val postCommentView = LayoutInflater.from(parent.context).inflate(R.layout.view_post_comments_list_element, parent, false)
        val postCommentViewHolder = PostCommentViewHolder(postCommentView)

        return postCommentViewHolder
    }

    override fun onBindViewHolder(holder: PostCommentViewHolder, position: Int) {
        val postComment = comments[position]

        holder.textViewBody.text = postComment.body
        holder.textViewCommenter.text = "- " + postComment.name + "(" + postComment.email + ")"
    }

    override fun getItemCount(): Int {
        return comments.size
    }

}