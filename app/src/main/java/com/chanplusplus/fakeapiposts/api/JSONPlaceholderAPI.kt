package com.chanplusplus.fakeapiposts.api

import com.chanplusplus.fakeapiposts.models.Comment
import com.chanplusplus.fakeapiposts.models.Post
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

interface JSONPlaceholderAPI {

    @GET("posts")
    fun getPosts() : Call<List<Post>>

    @GET("posts/{id}")
    fun getPost(@Path("id") id: Int) : Call<Post>

    @GET("posts/{id}/comments")
    fun getPostComments(@Path("id") id: Int) : Call<List<Comment>>

    companion object {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

        fun create() : JSONPlaceholderAPI {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(JSONPlaceholderAPI::class.java)
        }
    }

}