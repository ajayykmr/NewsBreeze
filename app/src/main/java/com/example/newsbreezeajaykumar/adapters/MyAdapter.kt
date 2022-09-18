package com.example.newsbreezeajaykumar.adapters

import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController


import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.newsbreezeajaykumar.R
import com.example.newsbreezeajaykumar.database.ArticleDatabase
import com.example.newsbreezeajaykumar.models.Article
import com.example.newsbreezeajaykumar.repository.NewsRepository
import com.example.newsbreezeajaykumar.ui.MainActivity
import com.example.newsbreezeajaykumar.ui.MyViewModel
import com.example.newsbreezeajaykumar.ui.MyViewModelProviderFactory


class MyAdapter(var viewModel: MyViewModel, var navController: NavController): RecyclerView.Adapter<MyAdapter.ArticleViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_view,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.title.text = article.title
        holder.description.text=article.description
        holder.date.text = article.publishedAt?.substring(0,10)
        Glide.with(holder.itemView)
            .load(article.urlToImage)
            .transform(CenterCrop(), RoundedCorners(20))
            .into(holder.image)

        /*holder.itemView.setOnClickListener{
            onItemClickListener?.let { it(article) }

        }*/
        holder.saveButton.setOnClickListener {
             viewModel.saveArticle(article)
                holder.saveButton.text = "Saved"
                holder.bookmarkIcon.setBackgroundResource(R.drawable.rounded_corner_green_bg)
                holder.bookmarkIcon.setImageResource(R.drawable.ic_bookmarks_filled)
        }
        holder.readButton.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", article)
            }
            viewModel.fromHomeScren = true
            navController.navigate(R.id.action_homeScreenFragment_to_articleDetailsFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val description: TextView = itemView.findViewById(R.id.tv_description)
        val image: ImageView = itemView.findViewById(R.id.iv_article_image)
        val date: TextView = itemView.findViewById(R.id.tv_date)
        val saveButton: Button = itemView.findViewById(R.id.btn_save_article)
        val readButton: Button = itemView.findViewById(R.id.btn_read_article)
        val bookmarkIcon: ImageView = itemView.findViewById(R.id.iv_bookmark_icon)
    }
}