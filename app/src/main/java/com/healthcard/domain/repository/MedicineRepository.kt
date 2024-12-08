package com.healthcard.domain.repository

import com.healthcard.domain.entity.DrugEntity
import com.healthcard.util.ResponseResult
import kotlinx.coroutines.flow.Flow

interface MedicineRepository {
    suspend fun getMedicineData():Flow<ResponseResult<List<DrugEntity>>>
}