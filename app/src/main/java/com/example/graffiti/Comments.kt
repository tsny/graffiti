package com.example.graffiti

class Comments : ArrayList<CommentsItem>()

data class CommentsItem(
    val message: String,
    val error: String,
    val `data`: Data,
    val kind: String
)
