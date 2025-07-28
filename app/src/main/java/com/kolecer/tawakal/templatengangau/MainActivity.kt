package com.kolecer.tawakal.templatengangau

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout
    lateinit var um: Umum
    lateinit var ngaloding: AlertDialog
    lateinit var llru: LinearLayout
    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val allGranted = permissions.entries.all { it.value }
            if (allGranted) {
                // Semua izin diberikan (atau pengguna memilih media tertentu di Android 14+)
                // Lanjutkan dengan logika akses media Anda
                loadMedia()
            } else {
                // Setidaknya satu izin ditolak atau pengguna tidak memilih media
                // Tangani kasus ketika izin tidak diberikan
                // Anda mungkin ingin menampilkan pesan kepada pengguna
                // atau menonaktifkan fungsionalitas yang memerlukan izin ini.
                Log.w("Permissions", "Izin tidak sepenuhnya diberikan.")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        um = Umum(this)
        um.aturWarna(this, um.getString("theme", "hejo"))
        um.temaGelap(um.getString("gelap", "off"))
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
        checkAndRequestMediaPermissions()
        llru = findViewById(R.id.ll_ruang_utama)
        ngaloding = um.progMuterBuka(this, "Memuat...")
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

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (shouldCancelBackPress()) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.FragmentMain, FragmentMain()).commit()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                    isEnabled = true
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }
    private fun checkAndRequestMediaPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.READ_MEDIA_IMAGES)
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        // Di Android 13 (API 33) dan lebih tinggi, READ_MEDIA_VISUAL_USER_SELECTED
        // akan diminta secara implisit jika READ_MEDIA_IMAGES atau READ_MEDIA_VIDEO
        // diminta dan aplikasi menargetkan API 34+ serta mendeklarasikan izin ini.
        // Namun, tidak ada salahnya menambahkannya secara eksplisit jika logika Anda memerlukannya.
        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) { // API 34
        //     if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
        //         != PackageManager.PERMISSION_GRANTED) {
        //         // Izin ini tidak diminta secara langsung, tetapi sistem akan menanganinya.
        //     }
        // }


        if (permissionsToRequest.isNotEmpty()) {
            requestPermissionsLauncher.launch(permissionsToRequest.toTypedArray())
        } else {
            // Izin sudah diberikan
            loadMedia()
        }
    }

    private fun loadMedia() {
        // Logika untuk memuat media setelah izin diberikan
        Log.d("MediaAccess", "Akses media diizinkan. Memuat media...")
        // ... implementasi Anda untuk mengakses gambar/video ...
    }

    // Penting: Tangani pemilihan ulang media jika diperlukan
    // Jika pengguna awalnya memilih beberapa foto/video, dan kemudian ingin mengubah pilihannya.
    fun reselectMedia() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE) { // API 34
            // Hanya minta READ_MEDIA_VISUAL_USER_SELECTED untuk memicu pemilihan ulang
            requestPermissionsLauncher.launch(arrayOf(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED))
        } else {
            // Untuk versi di bawah API 34, Anda mungkin perlu memicu permintaan izin standar lagi
            // atau menggunakan Photo Picker.
            checkAndRequestMediaPermissions()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navMain -> supportFragmentManager.beginTransaction()
                .replace(R.id.FragmentMain, FragmentMain()).commit()

            R.id.navSett -> {
                ngaloding.show()
                ngaloding.findViewById<TextView>(R.id.tvdprogA)?.text = "Memuat setting..."
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

    companion object
}