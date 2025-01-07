package org.quickness.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tokens")
data class TokenEntity(
    @PrimaryKey val id: Int, // El número del token (1, 2, 3, etc.)
    val tokenValue: String, // El valor del token
    val timestamp: Long // Fecha de la última actualización (epoch time en UTC-6)
)