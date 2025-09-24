package com.route.newsc42.ui.screens.news_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.newsc42.api.ApiManager
import com.route.newsc42.api.model.ArticleDM
import com.route.newsc42.api.model.SourceDM
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.PairSerializer

class NewsViewModel: ViewModel() {
    var isLoading = MutableLiveData<Boolean>()
    var sourcesErrorMessage = MutableLiveData<String?>()
    var articlesErrorMessage = MutableLiveData<Pair<String?, String>?>()
    var sources = MutableLiveData<List<SourceDM>?>()
    var articles = MutableLiveData<List<ArticleDM>?>()

    // Sources is the tabs that has the news articles.
    fun loadSources(categoryId: String) {
        sourcesErrorMessage.value = null
        isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiManager.getWebServices().loadSources(categoryId)
                sources.value = response.sources ?: emptyList()
                isLoading.value = false
            }catch (t: Throwable){
                isLoading.value = false
                sourcesErrorMessage.value = t.localizedMessage ?: "An error occurred"
            }
        }
    }

    // Articles is the news itself
    //
    fun loadArticles(sourceId: String) {
        sourcesErrorMessage.value = null
        isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiManager.getWebServices().loadArticles(sourceId)
                articles.value = response.articles ?: emptyList()
                isLoading.value = false
            }catch (t: Throwable){
                isLoading.value = false
                articlesErrorMessage.value = Pair(first = t.localizedMessage ?: "An error occurred", second = sourceId)
            }
        }
    }
}