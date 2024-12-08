package com.healthcard.domain.model

import com.google.gson.annotations.SerializedName
import com.healthcard.domain.entity.DrugEntity


data class ProblemsResponse(
    @SerializedName("problems")
    val problems: List<Map<String, List<Condition>>>
)

data class Condition(
    val medications: List<Medication>? = null,
)

data class Medication(
    val medicationsClasses: List<Map<String, List<Map<String, List<RemoteDrug>>>>>? = null
)

data class RemoteDrug(
    val name: String,
    val dose: String,
    val strength: String
)

fun RemoteDrug.toDrugEntity(): DrugEntity {
    return DrugEntity(
        id = 0,
        name = name,
        dose = dose,
        strength = strength
    )
}

fun ProblemsResponse.getAllDrugs(): List<RemoteDrug> {
    return problems.flatMap { problemMap ->
        problemMap.values.flatMap { conditions ->
            conditions.flatMap { condition ->
                condition.medications?.flatMap { medication ->
                    medication.medicationsClasses?.flatMap { medicationsClassesMap ->
                        medicationsClassesMap.values.flatMap { classNameMaps ->
                            classNameMaps.flatMap { associatedDrugMap ->
                                associatedDrugMap.values.flatten()
                            }
                        }
                    } ?: emptyList()
                } ?: emptyList()
            }
        }
    }
}