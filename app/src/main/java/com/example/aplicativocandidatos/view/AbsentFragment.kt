package com.example.aplicativocandidatos.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplicativocandidatos.constants.DataBaseConstants
import com.example.aplicativocandidatos.databinding.FragmentAbsentBinding
import com.example.aplicativocandidatos.view.adapter.GuestsAdapter
import com.example.aplicativocandidatos.view.listener.OnGuestListener
import com.example.aplicativocandidatos.viewmodel.GuestsViewModel

class AbsentFragment : Fragment() {

    private var _binding: FragmentAbsentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GuestsViewModel
    private val adapter = GuestsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        viewModel = ViewModelProvider(this).get(GuestsViewModel::class.java)
        _binding = FragmentAbsentBinding.inflate(inflater, container, false)

        //tipo do layout da recycler
        binding.recyclerGuests.layoutManager = LinearLayoutManager(context)

        // printa os dados na recycler view
        binding.recyclerGuests.adapter = adapter

        //quando clica em cima de um nome na recycler view
        val listener = object : OnGuestListener {
            override fun onClick(id: Int) {
                val intent = Intent(context, GuestFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(DataBaseConstants.GUEST.ID, id)
                intent.putExtras(bundle)
                startActivity(intent)
            }
            //deleta e gera a lista nova
            override fun onDelete(id: Int) {
                viewModel.delete(id)
                viewModel.getAbsent()
            }
        }
        adapter.attachListener(listener)
        observe()


        return binding.root
    }
    override fun onResume() {
        super.onResume()
        viewModel.getAbsent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //observa os dados e envia
    private fun observe() {
        viewModel.guests.observe(viewLifecycleOwner) {
            adapter.updatedGuests(it)
        }
    }
}