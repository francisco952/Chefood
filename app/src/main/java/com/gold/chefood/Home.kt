package com.gold.chefood

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        setSupportActionBar(findViewById(R.id.toolBar))
        supportActionBar?.setDisplayShowTitleEnabled(false)
        //function menu
        val iconMenu = findViewById<ImageView>(R.id.nav_bars)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        iconMenu.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawer(GravityCompat.START)
            }else{
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        //navigation
        val navView = findViewById<NavigationView>(R.id.nav_view)
        if(savedInstanceState == null){
            loadFragment(ListFragment())
        }
        navView.setNavigationItemSelectedListener { item: MenuItem ->
            when(item.itemId){
                R.id.nav_home -> loadFragment(ListFragment())
                R.id.nav_person -> loadFragment(UserFragment())
                R.id.nav_web -> loadFragment(WebFragment())
                R.id.nav_movie -> loadFragment(VideoFragment())
            }
            drawerLayout.closeDrawers()
            true
        }
    }
    //fragment
    fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}