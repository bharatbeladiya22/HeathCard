package com.healthcard.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.healthcard.domain.entity.DrugEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DrugDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(drugEntities: List<DrugEntity>)

    @Query("SELECT * FROM drug")
    fun getAll(): Flow<List<DrugEntity>>

    @Query("SELECT * FROM drug WHERE id = :id")
    suspend fun getMedicineById(id: Int): DrugEntity
}