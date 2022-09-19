package com.example.newsbreezeajaykumar.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.newsbreezeajaykumar.R
import com.example.newsbreezeajaykumar.models.Article
import com.example.newsbreezeajaykumar.ui.MyViewModel
import kotlinx.coroutines.Job

class BookmarkAdapter(var savedArticleList: List<Article>, var viewModel: MyViewModel, var navController: NavController): RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    private val fromBookmarkScreen = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        return BookmarkViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_view_bookmarks,parent,false)
        )
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val article = savedArticleList[position]
        holder.title.text = article.title
        holder.authorName.text = article.author
        holder.date.text = article.publishedAt?.substring(0,10)

        Glide.with(holder.itemView)
            .load(article.urlToImage)
            .transform(CenterCrop(), RoundedCorners(30))
            .into(holder.image)

        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", article)
            }
            viewModel.fromHomeScren = false
            navController.navigate(R.id.action_bookmarksFragment_to_articleDetailsFragment, bundle)
        }

        holder.itemView.setOnLongClickListener { _-> Boolean
            viewModel.deleteArticle(article)
            true
        }
    }

    override fun getItemCount(): Int {
        return savedArticleList.size
    }

    inner class BookmarkViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.tv_bm_title)
        val image: ImageView = itemView.findViewById(R.id.iv_bm_image)
        val date: TextView = itemView.findViewById(R.id.tv_bm_date)
        val authorName: TextView = itemView.findViewById(R.id.tv_bm_author_name)
    }
/*
    private var onItemClickListener: ((Article)->Unit)?=null

    fun setOnItemClickListener(listener: (Article)->Unit){
        onItemClickListener = listener
    }*/
}