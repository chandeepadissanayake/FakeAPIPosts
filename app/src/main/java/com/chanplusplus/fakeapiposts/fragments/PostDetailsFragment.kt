package com.chanplusplus.fakeapiposts.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.chanplusplus.fakeapiposts.R
import com.chanplusplus.fakeapiposts.api.JSONPlaceholderAPI
import com.chanplusplus.fakeapiposts.databinding.FragmentPostDetailsBinding
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
 * Use the [PostDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PostDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentPostDetailsBinding? = null
    private val binding: FragmentPostDetailsBinding
        get() = _binding!!

    private val args: PostDetailsFragmentArgs by navArgs()

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
        _binding = FragmentPostDetailsBinding.inflate(inflater, container, false)

        // Setting Properties of Elements
        binding.postDetailsLoader.setVisibilityAfterHide(View.GONE)

        return binding.root
    }

    private fun showPostDetailsLoader() {
        binding.postDetailsContent.visibility = View.GONE
        binding.postDetailsLoader.show()
    }

    private fun hidePostDetailsLoader() {
        binding.postDetailsLoader.hide()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showPostDetailsLoader()

        val apiRequestGetPost = JSONPlaceholderAPI.create().getPost(args.argNavPostId)
        apiRequestGetPost.enqueue(object: Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.body() != null) {
                    val post = response.body()!!

                    binding.textviewPostDetailsId.text = post.id.toString()
                    binding.textviewPostDetailsUserId.text = post.userId.toString()
                    binding.textviewPostDetailsTitle.text = post.title
                    binding.textviewPostDetailsBody.text = post.body

                    binding.buttonPostComments.setOnClickListener { _ ->
                        val actionNavigateToPostCommentsFragment = PostDetailsFragmentDirections.actionNavPostDetailsToPostComments(post.id)
                        val navController = view.findNavController()
                        navController.navigate(actionNavigateToPostCommentsFragment)
                    }

                    hidePostDetailsLoader()
                    binding.postDetailsContent.visibility = View.VISIBLE
                }
                else {
                    hidePostDetailsLoader()
                    Snackbar.make(view, "Unexpected empty response", Snackbar.LENGTH_LONG).show()

                    binding.buttonPostComments.setOnClickListener {
                        Snackbar.make(view, "Could not fetch comments without fetching the post details first.", Snackbar.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                hidePostDetailsLoader()
                Snackbar.make(view, "Could not fetch post details.", Snackbar.LENGTH_LONG).show()

                binding.buttonPostComments.setOnClickListener {
                    Snackbar.make(view, "Could not fetch comments without fetching the post details first.", Snackbar.LENGTH_LONG).show()
                }
            }

        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PostDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PostDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}