package com.example.aplicativocandidatos.view.viewholder

import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicativocandidatos.databinding.ActivityGuestFormBinding
import com.example.aplicativocandidatos.databinding.RowGuestBinding
import com.example.aplicativocandidatos.model.GuestModel
import com.example.aplicativocandidatos.view.listener.OnGuestListener
import com.google.firebase.firestore.FirebaseFirestore

class GuestViewHolder(private val bind: RowGuestBinding, private val listener: OnGuestListener) :
    RecyclerView.ViewHolder(bind.root) {

    fun bind(guest: GuestModel) {
        bind.textName.text = guest.name

        bind.textName.setOnClickListener {
            listener.onClick(guest.id)
        }

        //remocao de pessoa usando o clique longo no room
        bind.textName.setOnLongClickListener {

            AlertDialog.Builder(itemView.context)
                .setTitle("Remocao de convidado")
                .setMessage("Tem certeza que deseja remover?")
                .setPositiveButton("Sim") { dialog, which ->
                    listener.onDelete(guest.id)
                }
                .setNegativeButton("Nao",null)
                .create()
                .show()
            true
        }

    }
}