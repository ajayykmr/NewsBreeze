package com.example.newsbreezeajaykumar.models

data class ApiRespone(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)