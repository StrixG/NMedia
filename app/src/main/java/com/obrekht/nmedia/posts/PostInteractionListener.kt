package com.obrekht.nmedia.posts

import com.obrekht.nmedia.posts.repository.model.Post
interface PostInteractionListener {
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onLike(post: Post) {}
    fun onShare(post: Post) {}
}