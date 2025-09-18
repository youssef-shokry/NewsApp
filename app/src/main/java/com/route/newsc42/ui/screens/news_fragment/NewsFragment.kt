package com.route.newsc42.ui.screens.news_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.route.newsc42.api.ApiManager
import com.route.newsc42.api.model.ArticleDM
import com.route.newsc42.api.model.ArticlesResponse
import com.route.newsc42.api.model.BaseErrorResponse
import com.route.newsc42.api.model.SourceDM
import com.route.newsc42.api.model.SourcesResponse
import com.route.newsc42.databinding.FragmentNewsBinding
import com.route.newsc42.ui.screens.article_fragment.BottomSheetArticleFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable
import kotlin.collections.forEach

class NewsFragment(val categoryId: String) : Fragment() {
    lateinit var binding: FragmentNewsBinding
    val articleAdapter = ArticlesAdapter()
    companion object{
        const val ARTICLE_DETAILS = "articleDetails"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSources()
        setUpArticlesRecyclerView()
        intiAdapterListeners()
    }

    private fun intiAdapterListeners() {
        articleAdapter.onArticleClick = object : OnArticleClick {

            override fun articleClickListener(article: ArticleDM) {

                val bottomSheetArticleFragment = BottomSheetArticleFragment()
                bottomSheetArticleFragment.arguments = Bundle().apply {
                    putSerializable(ARTICLE_DETAILS, article)
                }

                bottomSheetArticleFragment.show(parentFragmentManager, null)
            }
        }
    }

    private fun setUpArticlesRecyclerView() {
        binding.articlesRecycler.adapter = articleAdapter
    }

    private fun loadSources() {
        hideError()
        showLoading()
        ApiManager.getWebServices().loadSources(categoryId)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse?>,
                    response: Response<SourcesResponse?>
                ) {
                    hideLoading()
                    if (response.isSuccessful && response.body() != null) {
                        showTabLayout(response.body()!!.sources ?: listOf())
                    } else {
                        val errorResponse = Gson().fromJson(
                            response.errorBody()?.string(),
                            BaseErrorResponse::class.java
                        )
                        showError(
                            errorResponse.message ?: "Something went wrong please try again later "
                        ) {
                            loadSources()
                        }
                    }
                }

                override fun onFailure(
                    call: Call<SourcesResponse?>,
                    t: Throwable
                ) {
                    hideLoading()
                    showError(t.localizedMessage) {
                        loadSources()
                    }
                }

            })
    }

    private fun showTabLayout(sources: List<SourceDM>) {
        binding.tabLayout.isVisible = true
        sources.forEach { source ->
            val tab = binding.tabLayout.newTab()
            tab.text = source.name
            tab.tag = source.id
            binding.tabLayout.addTab(tab)
        }
        loadArticles(sources[0].id ?: "")
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                loadArticles(tab!!.tag as String)
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabReselected(p0: TabLayout.Tab?) {}
        })
    }

    private fun loadArticles(sourceId: String) {
        showLoading()
        ApiManager.getWebServices().loadArticles(sourceId)
            .enqueue(object : Callback<ArticlesResponse> {
                override fun onResponse(
                    call: Call<ArticlesResponse?>,
                    response: Response<ArticlesResponse?>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        showArticlesList(response.body()!!.articles ?: listOf())
                    }
                }

                override fun onFailure(
                    call: Call<ArticlesResponse?>,
                    t: Throwable
                ) {
                }

            })
    }

    private fun showArticlesList(articles: List<ArticleDM>) {
        hideLoading()
        articleAdapter.submitList(articles)
    }

    fun hideLoading() {
        binding.loadingProgress.isVisible = false
    }

    fun showLoading() {
        binding.loadingProgress.isVisible = true
    }

    fun showError(errorMessages: String, onRetryClick: () -> Unit) {
        binding.errorView.root.isVisible = true
        binding.errorView.errorMessage.text = errorMessages
        binding.errorView.retryButton.setOnClickListener {
            onRetryClick()
        }
    }

    fun hideError() {
        binding.errorView.root.isVisible = false
    }

}