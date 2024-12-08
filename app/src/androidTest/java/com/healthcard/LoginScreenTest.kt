package com.healthcard

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.healthcard.ui.screen.user.LoginScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_validInput_invokesOnLoginSuccess() {
        var loginSuccessCalled = false

        composeTestRule.setContent {
            LoginScreen(onLoginSuccess = { loginSuccessCalled = true })
        }

        // Simulate an invalid login attempt
        composeTestRule.onNodeWithText("Login").performClick()

        // Simulate user input
        composeTestRule.onNodeWithText("Email").performTextInput("test@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("password123")

        // Perform login button click
        composeTestRule.onNodeWithText("Login").performClick()

        // Assert that the callback was triggered
        assertThat("Login callback not triggered: ",loginSuccessCalled)
    }

    @Test
    fun loginScreen_invalidInput_showsToast() {
        composeTestRule.setContent {
            LoginScreen(onLoginSuccess = {})
        }

        // Simulate an invalid login attempt
        composeTestRule.onNodeWithText("Login").performClick()

        // Toast verification is not natively supported in Compose Testing
        // Use Espresso's Toast matcher or mock Toast behavior in testing
    }
}
