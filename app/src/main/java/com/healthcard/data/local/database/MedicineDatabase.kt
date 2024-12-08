package com.healthcard.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.healthcard.data.local.dao.DrugDao
import com.healthcard.domain.entity.DrugEntity

@Database(entities = [DrugEntity::class], version = 1,exportSchema = false)
abstract class MedicineDatabase : RoomDatabase() {
    abstract fun medicineDao(): DrugDao
}