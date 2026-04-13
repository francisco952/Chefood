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
            createFragmentForMenu(item.itemId)?.let { loadFragment(it) }
            drawerLayout.closeDrawers()
            true
        }
    }

    // Punto unico de integracion para el equipo: cada nuevo modulo
    // solo necesita registrar su item de menu aqui.
    private fun createFragmentForMenu(menuId: Int): Fragment? {
        return when(menuId){
            R.id.nav_home -> ListFragment()
            R.id.nav_person -> UserFragment()
            R.id.nav_web -> WebFragment()
            R.id.nav_movie -> VideoFragment()
            else -> null
        }
    }

    //fragment
    fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}