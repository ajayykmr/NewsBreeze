package com.example.newsbreezeajaykumar.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.newsbreezeajaykumar.R
import com.example.newsbreezeajaykumar.database.ArticleDatabase
import com.example.newsbreezeajaykumar.repository.NewsRepository

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = MyViewModelProviderFactory(application, newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(MyViewModel::class.java)
    }
}