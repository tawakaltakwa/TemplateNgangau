package com.kolecer.tawakal.templatengangau

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

class FragmentMain : Fragment() {
    lateinit var ma: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
        ma = activity as MainActivity
        val bguri = ma.um.getString("bguri", "kosong")
        if (bguri != "kosong") {
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
                ma.um.aturLatarBelakangLinearLayout(requireActivity(), ma, imageUri)
            }
        }
    }

    companion object
}