package com.kolecer.tawakal.templatengangau

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val sharedPref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        when (sharedPref.getString("theme", "hejo")) {
            "hejo" -> setTheme(R.style.Base_Theme_TemplateNgangau_Hejo)
            "biru" -> setTheme(R.style.Base_Theme_TemplateNgangau_Biru)
            "beureum" -> setTheme(R.style.Base_Theme_TemplateNgangau_Beureum)
            "koneng" -> setTheme(R.style.Base_Theme_TemplateNgangau_Koneng)
            "kayas" -> setTheme(R.style.Base_Theme_TemplateNgangau_Kayas)
            "bungur" -> setTheme(R.style.Base_Theme_TemplateNgangau_Bungur)
            "oren" -> setTheme(R.style.Base_Theme_TemplateNgangau_Oren)
            else -> setTheme(R.style.Base_Theme_TemplateNgangau_Hejo) // Default to green theme
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(findViewById(R.id.toolbar))
        drawerLayout = findViewById<DrawerLayout>(R.id.main)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, findViewById(R.id.toolbar), R.string.open_nav, R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.FragmentMain, FragmentMain(), "frAktif").commit()
            navigationView.setCheckedItem(R.id.navMain)
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navMain -> supportFragmentManager.beginTransaction()
                .replace(R.id.FragmentMain, FragmentMain()).commit()

            R.id.navSett -> supportFragmentManager.beginTransaction()
                .replace(R.id.FragmentMain, FragmentSetting()).commit()

            R.id.navInfo -> supportFragmentManager.beginTransaction()
                .replace(R.id.FragmentMain, FragmentInfo()).commit()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}