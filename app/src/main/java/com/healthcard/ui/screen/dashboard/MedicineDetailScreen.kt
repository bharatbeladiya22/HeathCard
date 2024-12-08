package com.healthcard.ui.screen.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.healthcard.domain.entity.DrugEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineDetailScreen(drugEntity: DrugEntity, onBack: () -> Unit = {}) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = drugEntity.name) },
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .clickable { onBack() }
                            .padding(16.dp),
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(16.dp)

        ) {
            Text(text = "Dose: ${drugEntity.dose}")
            Text(text = "Strength: ${drugEntity.strength}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMedicineDetailScreen() {
    MedicineDetailScreen(
        drugEntity = DrugEntity(
            id = 1,
            name = "Medicine 1",
            dose = "Dose 1",
            strength = "Strength 1"
        )
    )
}