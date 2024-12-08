package com.healthcard.ui.screen

import com.healthcard.domain.entity.DrugEntity
import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenName {
    @Serializable
    data object Login : ScreenName()
    @Serializable
    data object Dashboard : ScreenName()
    @Serializable
    data class MedicineDetail(val drugEntity: DrugEntity) : ScreenName()

}