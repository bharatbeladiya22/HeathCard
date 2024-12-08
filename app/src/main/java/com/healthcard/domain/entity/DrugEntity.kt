package com.healthcard.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "drug")
data class DrugEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val dose: String,
    val strength: String
)