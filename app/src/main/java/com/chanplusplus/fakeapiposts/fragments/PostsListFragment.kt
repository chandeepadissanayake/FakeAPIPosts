package com.chanplusplus.fakeapiposts.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.chanplusplus.fakeapiposts.R
import com.chanplusplus.fakeapiposts.adapters.PostsAdapter
import com.chanplusplus.fakeapiposts.api.JSONPlaceholderAPI
import com.chanplusplus.fakeapiposts.databinding.FragmentPostsListBinding
import com.chanplusplus.fakeapiposts.models.Post
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PostsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PostsListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentPostsListBinding? = null
    private val binding: FragmentPostsListBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostsListBinding.inflate(inflater, container, false)

        // Setting required properties
        binding.postsLoader.setVisibilityAfterHide(View.GONE)

        return binding.root
    }

    private fun showPostsLoader() {
        binding.postsLoader.show()
        binding.postsContent.visibility = View.GONE
    }

    private fun hidePostsLoader() {
        binding.postsLoader.hide()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showPostsLoader()

        // Fetching Posts Data
        val apiRequestGetPosts = JSONPlaceholderAPI.create().getPosts()
        apiRequestGetPosts.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.body() != null) {
                    val posts = response.body()!!

                    val postsAdapter = PostsAdapter(posts)
                    binding.recyclerviewPosts.adapter = postsAdapter
                    binding.recyclerviewPosts.layoutManager = LinearLayoutManager(view.context)

                    hidePostsLoader()
                    binding.postsContent.visibility = View.VISIBLE
                }
                else {
                    hidePostsLoader()
                    Snackbar.make(view, "Empty response body!", Snackbar.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                binding.postsLoader.visibility = View.GONE
                binding.postsLoader.setVisibilityAfterHide(View.GONE)
                Snackbar.make(view, "Failed to fetch the posts", Snackbar.LENGTH_LONG).show()
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PostsListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PostsListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}