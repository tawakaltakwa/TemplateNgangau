package com.kolecer.tawakal.templatengangau

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment

class FragmentSetting : Fragment() {
    private lateinit var um: Umum

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Fragment Setting"
        val ma = activity as MainActivity
        val btHejo = view.findViewById<Button>(R.id.bTemaHijau)
        val btBeureum = view.findViewById<Button>(R.id.bTemaBeureum)
        val btBiru = view.findViewById<Button>(R.id.bTemaBiru)
        val btKoneng = view.findViewById<Button>(R.id.bTemaKoneng)
        val btKayas = view.findViewById<Button>(R.id.bTemaKayas)
        val btBungur = view.findViewById<Button>(R.id.bTemaBungur)
        val btOren = view.findViewById<Button>(R.id.bTemaOren)
        um = Umum(requireActivity())
        ma.um.progMuterUpdate(ma.ngaloding, "Memuat tema...")
        fun aturWarna(warna: String) {
            um.saveString("theme", warna)
            um.aturWarna(requireActivity(), warna)
            requireActivity().recreate()
        }
        btHejo.setOnClickListener { aturWarna("hejo") }
        btBiru.setOnClickListener { aturWarna("biru") }
        btBeureum.setOnClickListener { aturWarna("beureum") }
        btKoneng.setOnClickListener { aturWarna("koneng") }
        btKayas.setOnClickListener { aturWarna("kayas") }
        btBungur.setOnClickListener { aturWarna("bungur") }
        btOren.setOnClickListener { aturWarna("oren") }
        ma.um.progMuterUpdate(ma.ngaloding, "Cek tema gelap atau terang...")
        val switchGelap = view.findViewById<SwitchCompat>(R.id.switchTemaGelap)
        switchGelap.isChecked = um.getString("gelap", "off") == "on"
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
        ma.ngaloding.dismiss()
    }

    companion object
}