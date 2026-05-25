package com.example.dapuribuproject

import com.example.dapuribuproject.DataClass.Post

object PostUtil {
    fun filterLongTitle(posts: List<Post>, maxLength: Int): List<Post> {
        return posts.filter { it.name?.length!! <= maxLength }
    }
}