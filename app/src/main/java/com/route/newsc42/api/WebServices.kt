package com.route.newsc42.api

import com.route.newsc42.api.model.ArticlesResponse
import com.route.newsc42.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("/v2/top-headlines/sources")
    fun loadSources(@Query("category") categoryId: String): SourcesResponse

    @GET("/v2/everything")
    fun loadArticles(@Query("sources") sourceId: String): ArticlesResponse
}