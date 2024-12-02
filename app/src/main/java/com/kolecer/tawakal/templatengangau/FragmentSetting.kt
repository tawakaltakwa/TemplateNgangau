package com.kolecer.tawakal.templatengangau

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class FragmentSetting : Fragment() {
    private lateinit var um: Umum
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
            when (um.getString("theme", "hejo")) {
                "hejo" -> requireActivity().setTheme(R.style.Base_Theme_TemplateNgangau_Hejo)
                "biru" -> requireActivity().setTheme(R.style.Base_Theme_TemplateNgangau_Biru)
                "beureum" -> requireActivity().setTheme(R.style.Base_Theme_TemplateNgangau_Beureum)
                "koneng" -> requireActivity().setTheme(R.style.Base_Theme_TemplateNgangau_Koneng)
                "kayas" -> requireActivity().setTheme(R.style.Base_Theme_TemplateNgangau_Kayas)
                "bungur" -> requireActivity().setTheme(R.style.Base_Theme_TemplateNgangau_Bungur)
                "oren" -> requireActivity().setTheme(R.style.Base_Theme_TemplateNgangau_Oren)
                else -> requireActivity().setTheme(R.style.Base_Theme_TemplateNgangau_Hejo)
            }
            requireActivity().recreate()
        }
        btHejo.setOnClickListener { aturWarna("hejo") }
        btBiru.setOnClickListener { aturWarna("biru") }
        btBeureum.setOnClickListener { aturWarna("beureum") }
        btKoneng.setOnClickListener { aturWarna("koneng") }
        btKayas.setOnClickListener { aturWarna("kayas") }
        btBungur.setOnClickListener { aturWarna("bungur") }
        btOren.setOnClickListener { aturWarna("oren") }
    }

    companion object {

    }
}