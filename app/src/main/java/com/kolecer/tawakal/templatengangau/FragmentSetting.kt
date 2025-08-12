package com.kolecer.tawakal.templatengangau

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment

class FragmentSetting : Fragment() {
    private lateinit var um: Umum
    lateinit var ma: MainActivity
    private val pilihGambarLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let { uri ->
                    ma.um.saveString("bguri", uri.toString())
                    ma.um.aturLatarBelakangLinearLayout(ma, uri)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Fragment Setting"
        ma = activity as MainActivity
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
            um.aturWarna(warna)
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
        val bguri = ma.um.getString("bguri", "kosong")
        if (bguri != "kosong") {
            ma.um.aturLatarBelakang2(ma, bguri)
        }
        val btPilihBG = view.findViewById<Button>(R.id.settBpilihBG)
        val btResetBG = view.findViewById<Button>(R.id.settBresetBG)
        btPilihBG.text = "Pilih Gambar"
        btResetBG.text = "Reset"
        btPilihBG.setOnClickListener {
            bukaPemilihGambar()
        }
        btResetBG.setOnClickListener {
            ma.um.saveString("bguri", "kosong")
            ma.llru.background = null
        }
    }

    private fun bukaPemilihGambar() {
        val intent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
        pilihGambarLauncher.launch(intent)
    }

    companion object
}