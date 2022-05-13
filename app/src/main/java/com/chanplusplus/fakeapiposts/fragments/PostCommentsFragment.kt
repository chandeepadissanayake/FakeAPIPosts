package com.chanplusplus.fakeapiposts.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.chanplusplus.fakeapiposts.R
import com.chanplusplus.fakeapiposts.adapters.PostCommentsAdapter
import com.chanplusplus.fakeapiposts.api.JSONPlaceholderAPI
import com.chanplusplus.fakeapiposts.databinding.FragmentPostCommentsBinding
import com.chanplusplus.fakeapiposts.models.Comment
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
 * Use the [PostCommentsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PostCommentsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding : FragmentPostCommentsBinding? = null
    private val binding : FragmentPostCommentsBinding
        get() = _binding!!

    private val args : PostCommentsFragmentArgs by navArgs()

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
        _binding = FragmentPostCommentsBinding.inflate(inflater, container, false)

        // Setting properties of elements
        binding.postCommentsLoader.setVisibilityAfterHide(View.GONE)

        return binding.root
    }

    private fun showPostCommentsLoader() {
        binding.postCommentsContent.visibility = View.GONE
        binding.postCommentsLoader.show()
    }

    private fun hidePostCommentsLoader() {
        binding.postCommentsLoader.hide()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showPostCommentsLoader()

        binding.textviewPostCommentsTitle.text = "Comments for the Post #" + args.argNavPostId.toString()

        val apiRequestGetPostComments = JSONPlaceholderAPI.create().getPostComments(args.argNavPostId)
        apiRequestGetPostComments.enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.body() != null) {
                    val postComments = response.body()!!

                    val postCommentsAdapter = PostCommentsAdapter(postComments)
                    binding.recyclerviewPostComments.adapter = postCommentsAdapter
                    binding.recyclerviewPostComments.layoutManager = LinearLayoutManager(view.context)

                    hidePostCommentsLoader()
                    binding.postCommentsContent.visibility = View.VISIBLE
                }
                else {
                    hidePostCommentsLoader()
                    Snackbar.make(view, "Unexpected empty body response", Snackbar.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                hidePostCommentsLoader()
                Snackbar.make(view, "Could not fetch post comments list", Snackbar.LENGTH_LONG).show()
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
         * @return A new instance of fragment PostCommentsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PostCommentsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}