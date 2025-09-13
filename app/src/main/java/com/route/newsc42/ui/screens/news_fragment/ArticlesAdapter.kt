package com.route.newsc42.ui.screens.news_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.route.newsc42.api.model.ArticleDM
import com.route.newsc42.databinding.ItemArticleBinding

class ArticlesAdapter(var articles: List<ArticleDM> = emptyList<ArticleDM>()) :
    RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ArticleViewHolder,
        position: Int
    ) {
        val article = articles[position]
        holder.binding.articleTitle.text = article.title
        holder.binding.articleAuthor.text = article.author
        holder.binding.articleDate.text = article.publishedAt
        Glide.with(holder.binding.root.context)
            .load(article.urlToImage)
            .into(holder.binding.articleImage)
    }

    fun submitList(articles: List<ArticleDM>){
        this.articles = articles
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = articles.size


    inner class ArticleViewHolder(val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root)
}