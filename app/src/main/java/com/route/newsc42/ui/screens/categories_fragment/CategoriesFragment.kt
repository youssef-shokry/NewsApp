package com.route.newsc42.ui.screens.categories_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.route.newsc42.MainActivity
import com.route.newsc42.databinding.FragmentCategoriesBinding
import com.route.newsc42.ui.model.Category
import com.route.newsc42.ui.screens.news_fragment.NewsFragment

class CategoriesFragment : Fragment() {
    lateinit var binding: FragmentCategoriesBinding
    var adapter = CategoriesAdapter(Category.categories) { category ->
        (requireActivity() as MainActivity).showFragment(NewsFragment(category.id))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCategoriesRecyclerView()
    }

    private fun setUpCategoriesRecyclerView() {
        binding.categoriesRecyclerView.adapter = adapter
    }

}