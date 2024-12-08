package com.healthcard

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.healthcard.data.local.dao.DrugDao
import com.healthcard.data.local.database.MedicineDatabase
import com.healthcard.domain.entity.DrugEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MedicineDaoTest {

    private lateinit var database: MedicineDatabase
    private lateinit var medicineDao: DrugDao

    @Before
    fun setUp() {
        // Create an in-memory database
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MedicineDatabase::class.java
        ).allowMainThreadQueries() // Only for testing; avoid in production
         .build()

        medicineDao = database.medicineDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndReplaceMedicine() = runBlocking {
        // Arrange
        val medicine1 = DrugEntity(id = 1, name = "Aspirin", dose = "100mg", strength = "500mg")
        val medicine2 = DrugEntity(id = 1, name = "Aspirin", dose = "200mg", strength = "1000mg")
        medicineDao.insertAll(listOf(medicine1))

        // Act
        medicineDao.insertAll(listOf(medicine2))
        val retrievedMedicine = medicineDao.getMedicineById(1)

        // Assert
        assertNotNull(retrievedMedicine)
        assertEquals(medicine2, retrievedMedicine)
    }
}
