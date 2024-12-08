package com.healthcard.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.healthcard.domain.entity.DrugEntity
import com.healthcard.ui.screen.dashboard.DashboardScreen
import com.healthcard.ui.screen.dashboard.MedicineDetailScreen
import com.healthcard.ui.screen.user.LoginScreen
import com.healthcard.util.SharedPrefManager
import com.healthcard.util.serializableType
import kotlin.reflect.typeOf

@Composable
fun MainScreen(modifier: Modifier = Modifier, sharedPrefManager: SharedPrefManager) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = if(sharedPrefManager.isUserLoggedIn()) ScreenName.Dashboard else ScreenName.Login,
        modifier = modifier
    ) {
        composable<ScreenName.Login> {
            LoginScreen {
                navController.navigate(ScreenName.Dashboard) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        inclusive = true
                    }
                }
            }
        }
        composable<ScreenName.Dashboard> { DashboardScreen(navController = navController) }
        composable<ScreenName.MedicineDetail>(typeMap = mapOf(typeOf<DrugEntity>() to serializableType<DrugEntity>())) { backStackEntry ->
            val drugEntity = backStackEntry.toRoute<ScreenName.MedicineDetail>().drugEntity
            MedicineDetailScreen(drugEntity = drugEntity) {
                navController.popBackStack()
            }
        }
    }
}