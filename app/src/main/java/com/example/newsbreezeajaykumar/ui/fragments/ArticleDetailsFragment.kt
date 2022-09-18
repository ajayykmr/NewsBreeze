package com.example.newsbreezeajaykumar.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.newsbreezeajaykumar.R
import com.example.newsbreezeajaykumar.models.Article
import com.example.newsbreezeajaykumar.ui.MainActivity
import com.example.newsbreezeajaykumar.ui.MyViewModel
import org.w3c.dom.Text

class ArticleDetailsFragment : Fragment(R.layout.fragment_article_details) {

    lateinit var viewModel: MyViewModel
    val args: ArticleDetailsFragmentArgs by navArgs()
    private var date: TextView? = null
    private var title: TextView? = null
    private var authorName: TextView? = null
    private var position: TextView? = null
    private var saveButton: Button?=null
    private var profilePicture: ImageView? = null
    private var content: TextView? = null
    private var articleImage: ImageView? = null
    private var backButton: ImageView? = null
    private var bookmarkIcon: ImageView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        val article = args.article

        date = view.findViewById(R.id.tv_ad_date)
        title = view.findViewById(R.id.tv_ad_title)
        authorName = view.findViewById(R.id.tv_ad_author_name)
        position = view.findViewById(R.id.tv_ad_author_position)
        saveButton = view.findViewById(R.id.btn_ad_save)
        profilePicture = view.findViewById(R.id.iv_ad_profile_picture)
        content = view.findViewById(R.id.tv_ad_content)
        articleImage = view.findViewById(R.id.iv_article_image_detail)
        backButton = view.findViewById(R.id.iv_ad_back_button)
        bookmarkIcon = view.findViewById(R.id.iv_ad_bookmark_icon)

        setupArticleDetails(article, view)

        saveButton?.setOnClickListener {
            saveButton?.text = "Saved"
            bookmarkIcon?.setImageResource(R.drawable.ic_bookmarks_filled)
            viewModel.saveArticle(article)
        }
        backButton?.setOnClickListener {
            if(viewModel.fromHomeScren)
                findNavController().navigate(R.id.action_articleDetailsFragment_to_homeScreenFragment)
            else
                findNavController().navigate(R.id.action_articleDetailsFragment_to_bookmarksFragment)

        }

    }

    private fun setupArticleDetails(article: Article, view: View)
    {
        date?.text = article.publishedAt?.substring(0,10)
        title?.text = article.title
        authorName?.text = article.author
        position?.text = "UnknownPosition"

        content?.text = article.content?.substringBefore('[')
        //with(holder.itemView)
        /*Glide.with()
            .load(article.urlToImage)
            .transform(CenterCrop())
            .into(articleImage)*/
        Glide.with(view).load(article.urlToImage).centerCrop().into(articleImage!!)
        Glide.with(view).load(R.drawable.dummy_profile_pic).centerCrop().into(profilePicture!!)
    }
}
