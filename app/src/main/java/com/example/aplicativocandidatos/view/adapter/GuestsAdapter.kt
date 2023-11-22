package com.example.aplicativocandidatos.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicativocandidatos.databinding.RowGuestBinding
import com.example.aplicativocandidatos.model.GuestModel
import com.example.aplicativocandidatos.view.listener.OnGuestListener
import com.example.aplicativocandidatos.view.viewholder.GuestViewHolder

class GuestsAdapter : RecyclerView.Adapter<GuestViewHolder>(){

    private var guestList: List<GuestModel> = listOf()
    private lateinit var  listener: OnGuestListener

    //funcao que cria as linhas dentro da recycler view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val item = RowGuestBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return GuestViewHolder(item,listener)
    }

    // atribui valores as row da recycler
    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        holder.bind(guestList[position])
    }
    //pega o tamanho da lista levando em conta quantas pessoas estao registradas
    override fun getItemCount(): Int {
        return  guestList.count()
    }
    // atualiza a lista
    fun updatedGuests(list: List<GuestModel>){
        guestList = list
        notifyDataSetChanged()
    }
    fun attachListener(guestListener: OnGuestListener){
        listener = guestListener
    }
}