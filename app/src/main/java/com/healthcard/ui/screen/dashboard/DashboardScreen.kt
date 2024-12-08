package com.healthcard.ui.screen.dashboard

import android.app.UiModeManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.healthcard.domain.entity.DrugEntity
import com.healthcard.ui.screen.ScreenName
import com.healthcard.ui.theme.HealthCardTheme
import com.healthcard.util.ResponseResult

@Composable
fun DashboardScreen(navController: NavHostController, viewModel: DashboardVM = hiltViewModel()) {
    val greetingMessage by viewModel.greetingMessage.collectAsStateWithLifecycle()
    val apiState by viewModel.apiState.collectAsStateWithLifecycle()
    DashboardUI(viewModel.getEmailId(), greetingMessage, apiState) {
        navController.navigate(route = ScreenName.MedicineDetail(drugEntity = it))
    }
}

@Composable
private fun DashboardUI(
    email: String,
    greetingMessage: String,
    apiState: ResponseResult<List<DrugEntity>>,
    onClick: (drugEntity: DrugEntity) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = email, style = MaterialTheme.typography.titleSmall)
        Text(text = greetingMessage, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        when (apiState) {
            is ResponseResult.Error -> {
                ShowErrorUI((apiState as ResponseResult.Error).message)
            }

            ResponseResult.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is ResponseResult.Success -> {
                ShowData(
                    data = apiState.data,
                    onClick = onClick
                )
            }

            ResponseResult.None -> {}
        }
    }

}

@Composable
fun ShowData(data: List<DrugEntity>, onClick: (drugEntity: DrugEntity) -> Unit = {}) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(data) { medicine ->
            MedicationCard(drugEntity = medicine, onClick = onClick)
        }
    }
}

@Composable
fun ShowErrorUI(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message)
    }
}

@Composable
fun MedicationCard(drugEntity: DrugEntity, onClick: (drugEntity: DrugEntity) -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = drugEntity.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Dose: ${drugEntity.dose}")
            Text(text = "Strength: ${drugEntity.strength}")

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onClick(drugEntity)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "View Details")
            }
        }
    }
}

@Preview(showBackground = true, uiMode = UiModeManager.MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = UiModeManager.MODE_NIGHT_YES)
@Composable
fun PreviewDashboardScreen() {
    HealthCardTheme {
        Surface {
            val apiState = ResponseResult.Success(List(10) {
                DrugEntity(
                    id = it,
                    name = "Medicine $it",
                    dose = "Dose $it",
                    strength = "Strength $it"
                )
            }
            )
            DashboardUI(
                email = "bharat.beladiya@gmail.com",
                greetingMessage = "Good morning",
                apiState = apiState
            )

        }
    }
}