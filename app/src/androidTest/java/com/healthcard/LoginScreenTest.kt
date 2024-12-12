package com.healthcard

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.healthcard.ui.screen.user.LoginScreen
import com.healthcard.ui.screen.user.LoginViewModel
import com.healthcard.util.ResponseResult
import com.healthcard.util.SharedPrefManager
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginScreenTest {
    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        loginViewModel = LoginViewModel(sharedPrefManager)
    }

    @Test
    fun loginScreen_validInput_invokesOnLoginSuccess() = runTest {

        composeTestRule.setContent {
            LoginScreen(viewModel = loginViewModel, onLoginSuccess = {})
        }

        val email = "test@example.com"
        composeTestRule.onNodeWithText("Email").performTextInput("")
        composeTestRule.onNodeWithText("Password").performTextInput("")

        composeTestRule.onNodeWithText("Login").performClick()

        // Simulate user input
        composeTestRule.onNodeWithText("Email").performTextInput(email)
        composeTestRule.onNodeWithText("Password").performTextInput("password123")

        // Perform login button click
        composeTestRule.onNodeWithText("Login").performClick()

        val successMessage =
            loginViewModel.apiState.first { it is ResponseResult.Success } as ResponseResult.Success<String>
        Assert.assertEquals("Login Successful: $email", successMessage.data)

    }
}
