package com.route.newsc42.ui.screens.article_fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.route.newsc42.api.model.ArticleDM
import com.route.newsc42.databinding.FragmentBottomSheetArticleBinding
import com.route.newsc42.ui.screens.news_fragment.NewsFragment


class BottomSheetArticleFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentBottomSheetArticleBinding
    lateinit var article: ArticleDM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        article = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(NewsFragment.ARTICLE_DETAILS, ArticleDM::class.java) as ArticleDM
        } else {
            @Suppress("DEPRECATION")
            arguments?.getSerializable(NewsFragment.ARTICLE_DETAILS) as ArticleDM
        }
        binding = FragmentBottomSheetArticleBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext()).load(article.urlToImage).into(binding.articleImage)
        binding.articleDescription.text = article.content.toString()
        binding.viewArticleButton.setOnClickListener{
         // implicit intent
            val intent = Intent(Intent.ACTION_VIEW, article.url?.toUri())
            startActivity(intent)
        }
    }

}