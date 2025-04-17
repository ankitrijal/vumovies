package com.example.vumovies.api

import java.io.Serializable

data class Entity(
    val title: String,
    val director: String,
    val genre: String,
    val releaseYear: Int,
    val description: String
) : Serializable
