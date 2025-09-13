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

///Http Request
///Link -> BaseUrl/Api Name (Endpoint)/Query Params?
///Types -> GET, POST
///Body (POST) -> Json(Java script object notation) - xml - html
///Headers -> Json
///Json -> num - null - string - array[Json] - json
///Http response ->
/// status code int
/// Body
/// Headers

//fun main(){
//    val p: Person = Person("ahmed", 123, "ahmed@gmail.com",
//        listOf(Person("ahmed", 123, "email", listOf())))
//
//    mapOf<String, Any>(Pair("name", p.name),
//        Pair("age", p.age),
//        Pair("email", p.email),
//        Pair("friends", listOf())
//    )
//}
//data class Person(val name: String, val age: Int,
//                  val email: String,
//                  val friends: List<Person>)