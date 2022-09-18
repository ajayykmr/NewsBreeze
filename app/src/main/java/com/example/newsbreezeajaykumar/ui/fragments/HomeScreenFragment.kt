package com.example.newsbreezeajaykumar.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.findFragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsbreezeajaykumar.R
import com.example.newsbreezeajaykumar.adapters.MyAdapter
import com.example.newsbreezeajaykumar.ui.MainActivity
import com.example.newsbreezeajaykumar.ui.MyViewModel
import com.example.newsbreezeajaykumar.util.Constants.Companion.SEARCH_TIME_DELAY
import com.example.newsbreezeajaykumar.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {

    lateinit var viewModel: MyViewModel
    lateinit var newsAdapter: MyAdapter
    private var rv: RecyclerView? = null

    private var searchInput: EditText?= null
    val TAG = "HomeScreenFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookmarkImageButton: ImageButton = view.findViewById(R.id.ib_bookmark_button)

        bookmarkImageButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreenFragment_to_bookmarksFragment)
        }

        rv = view.findViewById(R.id.rv_home_screen)
        searchInput = view.findViewById(R.id.et_search)
        viewModel = (activity as MainActivity).viewModel

        setupRecyclerVeiw()

        breakingNews()

        //for seaerching
        var job: Job? = null
        searchInput?.addTextChangedListener {query->
            job?.cancel()
            job = MainScope().launch{
                delay(SEARCH_TIME_DELAY)
                query?.let{
                    if(query.toString().isNotEmpty()){
                        //viewModel.searchNews(query.toString())
                        searchNews(query.toString())
                    }
                    else{
                        breakingNews()
                    }
                }
            }
        }
    }

    private fun breakingNews() {
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occurred: $message")
                        Toast.makeText(activity, "An error occurred: $message", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun searchNews(query: String) {
        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occurred: $message")
                        Toast.makeText(activity, "An error occurred: $message", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        viewModel.searchNews(query)
    }

    private fun hideProgressBar(){
        //paginationProgressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar(){
        //paginationProgressBar.visibility = View.VISIBLE
    }
    private fun setupRecyclerVeiw(){

        newsAdapter = MyAdapter(viewModel, findNavController())
        rv?.adapter = newsAdapter
        rv?.layoutManager = LinearLayoutManager(activity)
    }
}
