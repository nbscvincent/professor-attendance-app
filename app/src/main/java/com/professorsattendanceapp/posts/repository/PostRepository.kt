package com.professorsattendanceapp.posts.repository

import com.professorsattendanceapp.posts.model.Post

interface PostRepository {

    // Retrieve all posts
    suspend fun getPosts(): List<Post>
}