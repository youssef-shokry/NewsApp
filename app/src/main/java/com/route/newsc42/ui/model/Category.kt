package com.route.newsc42.ui.model

import com.route.newsc42.R

data class Category(val id: String, val imageId: Int, val title: String) {
    //    business entertainment general health science sports technology
    companion object {
        val categories = listOf<Category>(
            Category("general", R.drawable.general_dark, "General"),
            Category("business", R.drawable.business_dark, "business"),
            Category("sports", R.drawable.sport_dark, "sports"),
            Category("technology", R.drawable.technology_dark, "technology"),
            Category("entertainment", R.drawable.entertainment_dark, "entertainment"),
            Category("health", R.drawable.health_dark, "health"),
            Category("science", R.drawable.science_dark, "science"),
            )
    }
}
