package com.route.newsc42.ui.screens.news_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.route.newsc42.api.model.ArticleDM
import com.route.newsc42.api.model.SourceDM
import com.route.newsc42.databinding.FragmentNewsBinding
import com.route.newsc42.ui.screens.bottom_sheet_fragment.BottomSheetArticleFragment
import kotlin.collections.forEach

class NewsFragment(val categoryId: String) : Fragment() {
    lateinit var viewModel: NewsViewModel
    lateinit var binding: FragmentNewsBinding
    val articleAdapter = ArticlesAdapter()
    companion object{
        const val ARTICLE_DETAILS = "articleDetails"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intiNewsViewModel()
        setUpObservers()
        setUpArticlesRecyclerView()
        intiAdapterListeners()
    }

    private fun intiNewsViewModel() {
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
    }

    private fun setUpObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading == true) showLoading()
            else hideLoading()
        }
        viewModel.sourcesErrorMessage.observe(viewLifecycleOwner) { sourcesErrorMessage ->
            if (sourcesErrorMessage.isNullOrEmpty()) hideError()
            else showError(errorMessages = sourcesErrorMessage,
                    onRetryClick = {viewModel.loadSources(categoryId)})

        }
        viewModel.articlesErrorMessage.observe(viewLifecycleOwner) { articlesErrorMessage ->
            if(articlesErrorMessage?.first.isNullOrEmpty()) hideError()
            else showError(errorMessages = articlesErrorMessage.first,
                onRetryClick = {viewModel.loadArticles(articlesErrorMessage.second)})
        }
        viewModel.sources.observe(viewLifecycleOwner) { sources ->
            if (sources.isNullOrEmpty()) return@observe
            else showTabLayout(sources)
        }
        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            if (articles.isNullOrEmpty()) return@observe
            showArticlesList(articles)
        }

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



    private fun showTabLayout(sources: List<SourceDM>) {
        binding.tabLayout.isVisible = true
        sources.forEach { source ->
            val tab = binding.tabLayout.newTab()
            tab.text = source.name
            tab.tag = source.id
            binding.tabLayout.addTab(tab)
        }
        viewModel.loadArticles(sourceId = sources[0].id ?: "")
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.loadArticles(tab!!.tag as String)
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabReselected(p0: TabLayout.Tab?) {}
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

    fun showError(errorMessages: String?, onRetryClick: () -> Unit) {
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