package com.route.newsc42

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.route.newsc42.databinding.ActivityMainBinding
import com.route.newsc42.ui.screens.categories_fragment.CategoriesFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpDrawerLayout()
        showFragment(CategoriesFragment())
    }

    fun setUpDrawerLayout(){
        var actionBarToggle = ActionBarDrawerToggle(
            this, binding.root, R.string.nav_open,
            R.string.nav_close
        )
        binding.root.addDrawerListener(actionBarToggle)
        //actionBarToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.icDrawer.setOnClickListener {
            if (binding.root.isOpen)
                binding.root.close()
            else binding.root.openDrawer(GravityCompat.START)
        }
        binding.navigationView.findViewById<TextView>(R.id.goToHome).setOnClickListener {

        }
    }

    fun showFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}