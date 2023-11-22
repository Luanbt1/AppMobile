package com.example.aplicativocandidatos.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.aplicativocandidatos.R
import com.example.aplicativocandidatos.constants.DataBaseConstants
import com.example.aplicativocandidatos.databinding.ActivityGuestFormBinding
import com.example.aplicativocandidatos.model.GuestModel
import com.example.aplicativocandidatos.viewmodel.GuestFormViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore


class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var viewModel: GuestFormViewModel
    private var guestId = 0

    private val banco by lazy {
        FirebaseFirestore.getInstance()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //instancia a view model
        viewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        //botao que salva dados na interface chamando a funcao on click
        binding.buttonSave.setOnClickListener(this)
        binding.radioPresent.isChecked = true

        //botao de deletar que chama delete no firebase
        binding.btnDeletar.setOnClickListener {
            deletarDadosFireBase()
        }

        observe()
        loadData()

    }
    //funcao que salva e envia os dados para a viewmodel
    override fun onClick(v: View) {

        // funcao que manda os dados para a view model
        if (v.id == R.id.button_save) {
            val name = binding.editName.text.toString()
            val presence = binding.radioPresent.isChecked

            val model = GuestModel().apply {
                this.id = guestId
                this.name = name
                this.presence = presence
            }
            viewModel.save(model)
            salvarDadosFireBase()
        }
    }

    private fun salvarDadosFireBase() {

        val name = binding.editName.text.toString()
        val presenca = binding.radioPresent.isChecked
        val id = binding.editId.text.toString()

        val list = mapOf(
            "nome" to name,
            "presenca" to presenca
        )
        banco.collection("convidados")
            .document(id)
            .set(list)
    }

    private fun deletarDadosFireBase(){
        val name = binding.editName.text.toString()
        val presenca = binding.radioPresent.isChecked
        val id = binding.editId.text.toString()

        val dados = mapOf(
            "nome" to name,
            "presenca" to presenca
        )

        val referenciaDados = banco
            .collection("convidados")
            .document(id)
        referenciaDados.delete()
    }
        //fica observando e enviando dados para a viewmodel
    private fun observe() {
        viewModel.guest.observe(this, Observer {
            binding.editName.setText(it.name)
            if (it.presence) {
                binding.radioPresent.isChecked = true
            } else {
                binding.radioAbsent.isChecked = true
            }
        })

        viewModel.saveGuest.observe(this, Observer {
            if (it != "") {
                Toast.makeText(application, it, Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    //carrega os dados do convidados caso n forem nulos
    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            guestId = bundle.getInt(DataBaseConstants.GUEST.ID)
            viewModel.get(guestId)
        }
    }
}