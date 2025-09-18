package com.route.newsc42.ui.screens.news_fragment

import com.route.newsc42.api.model.ArticleDM

interface OnArticleClick {
    fun articleClickListener(article: ArticleDM)
}