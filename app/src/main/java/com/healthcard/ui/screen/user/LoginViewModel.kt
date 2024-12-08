package com.healthcard.ui.screen.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthcard.util.ResponseResult
import com.healthcard.util.SharedPrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sharedPrefManager: SharedPrefManager
) : ViewModel() {

    private val _apiState = MutableStateFlow<ResponseResult<String>>(ResponseResult.None)
    val apiState = _apiState

    fun loginUser(email: String) {
        viewModelScope.launch {
            _apiState.value = ResponseResult.Loading
            delay(2000)
            sharedPrefManager.saveEmail(email)
            sharedPrefManager.setUserLoggedIn(true)
            _apiState.value = ResponseResult.Success("Login Successful")
        }

    }

}