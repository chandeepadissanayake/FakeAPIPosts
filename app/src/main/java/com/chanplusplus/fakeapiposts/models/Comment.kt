package com.chanplusplus.fakeapiposts.models

data class Comment(val id: Int, val postId: Int, val name: String, val email: String, val body: String)
