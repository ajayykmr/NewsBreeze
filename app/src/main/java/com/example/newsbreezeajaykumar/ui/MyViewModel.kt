package com.example.newsbreezeajaykumar.ui

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import android.os.Build.VERSION_CODES.M
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsbreezeajaykumar.NewsApplication
import com.example.newsbreezeajaykumar.models.ApiRespone
import com.example.newsbreezeajaykumar.models.Article
import com.example.newsbreezeajaykumar.repository.NewsRepository
import com.example.newsbreezeajaykumar.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MyViewModel (app: Application, val newsRepository: NewsRepository): AndroidViewModel(app){
    var fromHomeScren: Boolean = true

    val breakingNews: MutableLiveData<Resource<ApiRespone>> = MutableLiveData()


    val searchNews: MutableLiveData<Resource<ApiRespone>> = MutableLiveData()


    init {
        getBreakingNews("in")
    }
    fun getBreakingNews(countryCode: String) = viewModelScope.launch{
        safeBreakingNewsCall(countryCode)
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        safeSearchNewsCall(searchQuery)
    }


    private fun handleBreakingNewsResponse(response: Response<ApiRespone>): Resource<ApiRespone>{
        if (response.isSuccessful){
            response.body()?.let{ resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<ApiRespone>): Resource<ApiRespone>{
        if (response.isSuccessful){
            response.body()?.let{ resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch{
        newsRepository.deleteArticle(article)
    }
    private suspend fun safeBreakingNewsCall(countryCode:String){
        breakingNews.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()){
                val response = newsRepository.getBreakingNews(countryCode)
                breakingNews.postValue(handleSearchNewsResponse(response))
            }else{
                breakingNews.postValue(Resource.Error("No Internet Connection"))
            }


        }catch (t: Throwable){
            when(t){
                is IOException -> breakingNews.postValue(Resource.Error("Network Failure"))
                else -> breakingNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }
    private suspend fun safeSearchNewsCall(searchQuery: String){
        searchNews.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()){
                val response = newsRepository.searchNews(searchQuery)
                searchNews.postValue(handleBreakingNewsResponse(response))
            }else{
                searchNews.postValue(Resource.Error("No Internet Connection"))
            }


        }catch (t: Throwable){
            when(t){
                is IOException -> breakingNews.postValue(Resource.Error("Network Failure"))
                else -> breakingNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun hasInternetConnection(): Boolean{
        val connectivityManager = getApplication<NewsApplication>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabalities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabalities.hasTransport(TRANSPORT_WIFI) -> true
                capabalities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabalities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }

    }
}