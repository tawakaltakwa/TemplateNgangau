package com.kolecer.tawakal.templatengangau

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

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
            ma.um.aturLatarBelakang2(ma, bguri)
        }
    }

    companion object
}