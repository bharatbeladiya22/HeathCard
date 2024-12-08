package com.healthcard.data.repository

import com.healthcard.data.local.dao.DrugDao
import com.healthcard.domain.entity.DrugEntity
import com.healthcard.data.remote.ApiService
import com.healthcard.domain.model.getAllDrugs
import com.healthcard.domain.model.toDrugEntity
import com.healthcard.domain.repository.MedicineRepository
import com.healthcard.util.ResponseResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicineRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val drugDao: DrugDao
): MedicineRepository {

    override suspend fun getMedicineData(): Flow<ResponseResult<List<DrugEntity>>> {
        return flow {
            emit(ResponseResult.Loading)
            val localData = drugDao.getAll().first() // Fetch data from local DB, first record only

            if (localData.isNotEmpty()) {
                emit(ResponseResult.Success(localData))  // Return cached data if available
            }

            val response = apiService.getMedicines()
            if (response.isSuccessful) {
                val allRemoteDrugs: List<com.healthcard.domain.model.RemoteDrug> = response.body()?.getAllDrugs()?: emptyList()
                val entities = allRemoteDrugs.map { it.toDrugEntity() }
                drugDao.insertAll(entities)
                emit(ResponseResult.Success(entities))
            }
        }
    }
}