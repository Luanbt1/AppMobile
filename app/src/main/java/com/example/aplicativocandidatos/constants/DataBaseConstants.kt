package com.example.aplicativocandidatos.constants

// classe de constantes para facilitar a utilizacao do banco
class DataBaseConstants private constructor() {

    object GUEST {
        const val ID = "guestid"
        const val TABLE_NAME = "Guest"

        object COLUMNS {
            const val ID = "id"
            const val NAME = "name"
            const val PRESENCE = "presence"
        }
    }
}