package com.example.newsbreezeajaykumar.ui.fragments

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsbreezeajaykumar.R
import com.example.newsbreezeajaykumar.adapters.BookmarkAdapter
import com.example.newsbreezeajaykumar.adapters.MyAdapter
import com.example.newsbreezeajaykumar.ui.MainActivity
import com.example.newsbreezeajaykumar.ui.MyViewModel

class BookmarksFragment : Fragment(R.layout.fragment_bookmarks) {

    lateinit var viewModel: MyViewModel
    lateinit var bookmarkAdapter: BookmarkAdapter
    private var rv: RecyclerView? = null
    private var backButton: ImageView? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        //setUpRecyclerView()

        rv = view.findViewById(R.id.rv_bm)
        backButton = view.findViewById(R.id.iv_bm_back_button)
        setupRecyclerView()
        backButton?.setOnClickListener {
            findNavController().navigate(R.id.action_bookmarksFragment_to_homeScreenFragment)
        }

    }

    private fun setupRecyclerView() {
        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer{savedArticles ->
            bookmarkAdapter = BookmarkAdapter(savedArticles, viewModel, findNavController())
            rv?.adapter=BookmarkAdapter(savedArticles, viewModel, findNavController())
            rv?.layoutManager = LinearLayoutManager(activity)
        })
    }

}
