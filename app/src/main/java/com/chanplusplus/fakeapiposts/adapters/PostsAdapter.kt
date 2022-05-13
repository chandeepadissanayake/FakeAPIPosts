package com.chanplusplus.fakeapiposts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.chanplusplus.fakeapiposts.R
import com.chanplusplus.fakeapiposts.fragments.PostDetailsFragmentArgs
import com.chanplusplus.fakeapiposts.fragments.PostsListFragment
import com.chanplusplus.fakeapiposts.fragments.PostsListFragmentDirections
import com.chanplusplus.fakeapiposts.models.Post

class PostsAdapter(private val posts: List<Post>) : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewId: TextView = view.findViewById(R.id.textview_id)
        val textViewTitle: TextView = view.findViewById(R.id.textview_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val postView = LayoutInflater.from(parent.context).inflate(R.layout.view_posts_list_element, parent, false)
        val postViewHolder = PostViewHolder(postView)

        postViewHolder.itemView.setOnClickListener { view ->
            val postId = postViewHolder.adapterPosition + 1

            val navController = view.findNavController()
            val actionNavigateToPostDetailsFragment = PostsListFragmentDirections.actionNavPostsListToPostDetails(postId)
            navController.navigate(actionNavigateToPostDetailsFragment)
        }

        return postViewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.textViewId.text = posts[position].id.toString()
        holder.textViewTitle.text = posts[position].title
    }

    override fun getItemCount() = posts.size

}