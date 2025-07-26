package com.kolecer.tawakal.templatengangau

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import java.text.DecimalFormat
import androidx.core.content.edit

class Umum(c: Context) {
    private val sharedPreferences: SharedPreferences =
        c.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    lateinit var alertDialog: AlertDialog
    private var progMuter: ProgressBar? = null
    private var tvProgMuter: TextView? = null

    fun saveString(key: String, value: String) {
        sharedPreferences.edit() { putString(key, value) }
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

    fun formatDuit(angka: Int): String {/*val nf = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        var d = nf.format(angka)*/
        val formatter = DecimalFormat("#.###")
        return "Rp. " + formatter.format(angka)
    }

    fun toastSederhana(context: Context, pesan: String) {
        Toast.makeText(context, pesan, Toast.LENGTH_LONG).show()
    }

    fun dialogSederhana(context: Context, judul: String, pesan: String) {
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

    fun progMuterBuka(context: Context, judul: String) {
        val builder = AlertDialog.Builder(context)
        val judulL = LayoutInflater.from(context).inflate(R.layout.dialog_judul, null) as TextView
        judulL.text = judul
        builder.setCustomTitle(judulL).setCancelable(false)
        val dView = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null)
        progMuter = dView.findViewById(R.id.progressBar)
        tvProgMuter = dView.findViewById(R.id.tvdprogA)
        tvProgMuter!!.text = "Proses..."
        builder.setView(dView)
        alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawableResource(R.drawable.dialog_bg)
        alertDialog.show()
    }

    fun progMuterUpdate(teksA: String) {
        try {
            alertDialog?.let { dialog ->
                if (dialog.isShowing) {
                    tvProgMuter!!.text = teksA
                }
            }
        } catch (_: Exception) {

        }
    }

    fun progMuterTutup() {
        try {
            alertDialog?.let { dialog ->
                if (dialog.isShowing) {
                    dialog.dismiss()
                }
            }
        } catch (_: Exception) {

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
//        val dialog = d.create()
        d.window?.setBackgroundDrawableResource(R.drawable.dialog_bg)
        d.show()
    }
}