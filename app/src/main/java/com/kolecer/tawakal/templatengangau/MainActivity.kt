package com.kolecer.tawakal.templatengangau

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
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
    private lateinit var um: Umum
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        um = Umum(this)
        um.aturWarna(this, um.getString("theme", "hejo"))
        um.temaGelap(um.getString("gelap", "off"))
        Companion.initUmum(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(findViewById(R.id.toolbar))
        drawerLayout = findViewById(R.id.main)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val headerView = navigationView.getHeaderView(0)
        val headerTitle: TextView = headerView.findViewById(R.id.header_judul)
        headerTitle.text = "MENU"
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

            R.id.navSett -> {
                Companion.PMbuka("Memuat Setting")
                supportFragmentManager.beginTransaction()
                    .replace(R.id.FragmentMain, FragmentSetting()).commit()
            }

            R.id.navInfo -> supportFragmentManager.beginTransaction()
                .replace(R.id.FragmentMain, FragmentInfo()).commit()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun shouldCancelBackPress(): Boolean {
        var tahan = false
        if (supportActionBar?.title?.toString() != getString(R.string.app_name)) {
            tahan = true
        }
        return tahan
    }

    override fun onBackPressed() {
        if (shouldCancelBackPress()) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.FragmentMain, FragmentMain()).commit()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        lateinit var um: Umum
        lateinit var context: Context
        fun initUmum(c: Context) {
            context = c
            um = Umum(c)
        }

        fun PMbuka(j: String) {
            um.ProgMuterBuka(context, j)
        }

        fun PMup(j: String) {
            um.ProgMuterUpdate(j)
        }

        fun PMtutup() {
            um.ProgMuterTutup()
        }
    }
}