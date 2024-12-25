package com.kolecer.tawakal.templatengangau

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class FragmentSetting : Fragment() {
    private lateinit var um: Umum
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Fragment Setting"
        val btHejo = view.findViewById<Button>(R.id.bTemaHijau)
        val btBeureum = view.findViewById<Button>(R.id.bTemaBeureum)
        val btBiru = view.findViewById<Button>(R.id.bTemaBiru)
        val btKoneng = view.findViewById<Button>(R.id.bTemaKoneng)
        val btKayas = view.findViewById<Button>(R.id.bTemaKayas)
        val btBungur = view.findViewById<Button>(R.id.bTemaBungur)
        val btOren = view.findViewById<Button>(R.id.bTemaOren)
        um = Umum(requireActivity())
        fun aturWarna(warna: String) {
            um.saveString("theme", warna)
            um.aturWarna(requireActivity(), warna)
            requireActivity().recreate()
        }
        MainActivity.Companion.PMtutup()
        btHejo.setOnClickListener { aturWarna("hejo") }
        btBiru.setOnClickListener { aturWarna("biru") }
        btBeureum.setOnClickListener { aturWarna("beureum") }
        btKoneng.setOnClickListener { aturWarna("koneng") }
        btKayas.setOnClickListener { aturWarna("kayas") }
        btBungur.setOnClickListener { aturWarna("bungur") }
        btOren.setOnClickListener { aturWarna("oren") }
        val switchGelap = view.findViewById<Switch>(R.id.switchTemaGelap)
        if (um.getString("gelap", "on") == "on") {
            switchGelap.isChecked = true
        } else {
            switchGelap.isChecked = false
        }
        switchGelap.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                um.saveString("gelap", "on")
                um.temaGelap("on")
            } else {
                um.saveString("gelap", "off")
                um.temaGelap("off")
            }
            requireActivity().recreate()
        }
    }

    companion object {

    }
}