package com.obrekht.nmedia.posts.ui

import com.obrekht.nmedia.posts.model.Post
interface PostInteractionListener {
    fun onClick(post: Post) {}
    fun onVideoClick(post: Post) {}
    fun onLike(post: Post) {}
    fun onShare(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
}
