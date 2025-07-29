package com.kolecer.tawakal.templatengangau

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import java.io.FileNotFoundException
import java.io.InputStream
import java.text.DecimalFormat

class Umum(c: Context) {
    private val sharedPreferences: SharedPreferences =
        c.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    private var progMuter: ProgressBar? = null
    private var tvProgMuter: TextView? = null
    private var handler: Handler? = null

    fun saveString(key: String, value: String) {
        sharedPreferences.edit { putString(key, value) }
    }

    fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun aturWarna(context: Context, warna: String) {
        when (warna) {
            "hejo" -> context.setTheme(R.style.Base_Theme_TemplateNgangau_Hejo)
            "biru" -> context.setTheme(R.style.Base_Theme_TemplateNgangau_Biru)
            "beureum" -> context.setTheme(R.style.Base_Theme_TemplateNgangau_Beureum)
            "koneng" -> context.setTheme(R.style.Base_Theme_TemplateNgangau_Koneng)
            "kayas" -> context.setTheme(R.style.Base_Theme_TemplateNgangau_Kayas)
            "bungur" -> context.setTheme(R.style.Base_Theme_TemplateNgangau_Bungur)
            "oren" -> context.setTheme(R.style.Base_Theme_TemplateNgangau_Oren)
            else -> context.setTheme(R.style.Base_Theme_TemplateNgangau_Hejo)
        }
    }

    fun temaGelap(tema: String) {
        if (tema == "on") {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    fun aturLatarBelakangLinearLayout(context: Context, ma: MainActivity, imageUri: Uri) {
        try {
            val inputStream: InputStream? = ma.contentResolver.openInputStream(imageUri)
            val drawable = Drawable.createFromStream(inputStream, imageUri.toString())
            ma.llru.background = drawable
            inputStream?.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            ma.um.toastSederhana(context, "File gambar tidak ditemukan")
        } catch (e: Exception) {
            e.printStackTrace()
            ma.um.toastSederhana(context, "Gagal memuat gambar")
        }
    }

    fun aturLatarBelakang2(context: Context, ma: MainActivity, bguri: String) {
        val bguriparse = bguri.let { Uri.parse(it) }
        val contentResolver = ma.contentResolver
        val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        val imageUri: Uri? = bguriparse
        imageUri?.let {
            try {
                contentResolver.takePersistableUriPermission(it, takeFlags)
            } catch (e: SecurityException) {
                Log.e("PermissionError", "Failed to take persistable permission", e)
            }
        }
        if (imageUri != null) {
            ma.um.aturLatarBelakangLinearLayout(context, ma, imageUri)
        }
    }

    fun formatDuit(angka: Int): String {/*val nf = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        var d = nf.format(angka)*/
        val formatter = DecimalFormat("#.###")
        return "Rp. " + formatter.format(angka)
    }

    fun toastSederhana(context: Context, pesan: String) {
        Toast.makeText(context, pesan, Toast.LENGTH_LONG).show()
    }

    fun dialogInfo(context: Context, judul: String, pesan: String) {
        val builder = AlertDialog.Builder(context)
        val judulL = LayoutInflater.from(context).inflate(R.layout.dialog_judul, null) as TextView
        judulL.text = judul
        builder.setCustomTitle(judulL).setMessage(pesan).setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_bg)
        dialog.show()
    }

    // tambahkan namaAlertDialog.show() dan namaAlertDialog.dismis() saat digunakan
    fun progMuterBuka(context: Context, judul: String): AlertDialog {
        val builder = AlertDialog.Builder(context)
        val judulL = LayoutInflater.from(context).inflate(R.layout.dialog_judul, null) as TextView
        judulL.text = judul
        builder.setCustomTitle(judulL).setCancelable(false)
        val dView = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null)
        progMuter = dView.findViewById(R.id.progressBar)
        tvProgMuter = dView.findViewById(R.id.tvdprogA)
        tvProgMuter!!.text = "Proses..."
        builder.setView(dView)
        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawableResource(R.drawable.dialog_bg)
        return alertDialog
    }

    fun progMuterUpdate(aD: AlertDialog, teksA: String) {
        if (handler == null) {
            handler = Handler(Looper.getMainLooper())
        }
        handler?.post {
            aD.findViewById<TextView>(R.id.tvdprogA)?.text = teksA
        }
    }

    fun dialogKonfirmasi(context: Context, judul: String, pesan: String, konfirmasi: () -> Unit) {
        val builder = AlertDialog.Builder(context)
        val judulL = LayoutInflater.from(context).inflate(R.layout.dialog_judul, null) as TextView
        judulL.text = judul
        builder.setCustomTitle(judulL).setMessage(pesan)
        builder.setPositiveButton("Ya") { dialog, _ ->
            konfirmasi.invoke()
            dialog.dismiss()
        }
        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_bg)
        dialog.show()
    }

    // menyusun dialog selanjutnya disambung denga DialogTampil()
    fun dialogSusun(
        c: Context, judul: String, pesan: String, vBody: View
    ): AlertDialog.Builder {
        val builder = AlertDialog.Builder(c)
        val judulL = LayoutInflater.from(c).inflate(R.layout.dialog_judul, null) as TextView
        judulL.text = judul
        builder.setCustomTitle(judulL)
        if (pesan.isNotEmpty()) {
            builder.setMessage(pesan)
        }
        builder.setView(vBody)
        builder.create().window?.setBackgroundDrawableResource(R.drawable.dialog_bg)
        return builder
    }

    fun dialogTampil(d: AlertDialog) {
        d.window?.setBackgroundDrawableResource(R.drawable.dialog_bg)
        d.show()
    }
}