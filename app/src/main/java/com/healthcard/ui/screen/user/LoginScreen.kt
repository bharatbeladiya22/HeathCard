package com.healthcard.ui.screen.user

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.healthcard.R
import com.healthcard.domain.entity.DrugEntity
import com.healthcard.ui.screen.dashboard.MedicineDetailScreen
import com.healthcard.ui.screen.dashboard.ShowData
import com.healthcard.ui.screen.dashboard.ShowErrorUI
import com.healthcard.ui.theme.HealthCardTheme
import com.healthcard.util.ResponseResult

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel(),onLoginSuccess: () -> Unit) {
    val apiState by viewModel.apiState.collectAsStateWithLifecycle()

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
            onLoginSuccess()
        }

        ResponseResult.None -> {
            LoginUI(onLoginApi = {
                viewModel.loginUser(it)
            })
        }
    }
}

@Composable
private fun LoginUI(onLoginApi: (email: String) -> Unit) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val context = LocalContext.current
        Text(text = "Medicine App", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.email)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.password)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            if (email.isNotEmpty() && password.isNotEmpty()) {
                onLoginApi(email)
            } else {
                Toast.makeText(
                    context,
                    "Please enter valid credentials",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }) {
            Text(stringResource(R.string.login))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMedicineDetailScreen() {
    HealthCardTheme {
        Surface {
            LoginUI() {

            }
        }
    }
}
