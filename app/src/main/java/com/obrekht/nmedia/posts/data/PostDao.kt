package com.obrekht.nmedia.posts.data

import com.obrekht.nmedia.posts.model.Post

interface PostDao {
    fun getAll(): List<Post>
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post): Post
}