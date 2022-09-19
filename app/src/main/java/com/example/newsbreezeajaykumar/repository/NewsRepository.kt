package com.example.newsbreezeajaykumar.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsbreezeajaykumar.api.RetrofitInstance
import com.example.newsbreezeajaykumar.database.ArticleDatabase
import com.example.newsbreezeajaykumar.models.ApiRespone
import com.example.newsbreezeajaykumar.models.Article
import com.example.newsbreezeajaykumar.util.Resource
import retrofit2.Retrofit

class NewsRepository(val database: ArticleDatabase): ViewModel() {
    suspend fun getBreakingNews(countryCode: String) =
        RetrofitInstance.api.getBreakingNews(countryCode)

    suspend fun searchNews(searchQuery: String) =
        RetrofitInstance.api.searchForNews(searchQuery)

    suspend fun upsert(article: Article) = database.getArticleDao().upsert(article)

    fun getSavedNews() = database.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = database.getArticleDao().deleteArticle(article)
}