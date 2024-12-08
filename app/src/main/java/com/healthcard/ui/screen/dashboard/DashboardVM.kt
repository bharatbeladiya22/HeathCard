package com.healthcard.ui.screen.dashboard

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthcard.domain.entity.DrugEntity
import com.healthcard.domain.repository.MedicineRepository
import com.healthcard.util.ResponseResult
import com.healthcard.util.SharedPrefManager
import com.healthcard.util.greetings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardVM @Inject constructor(
    private val medicineRepository: MedicineRepository,
    private val sharedPrefManager: SharedPrefManager
) : ViewModel() {

    private val _greetingMessage = MutableStateFlow("")
    val greetingMessage: StateFlow<String> = _greetingMessage

    private fun updateGreeting() {
        _greetingMessage.value = Calendar.getInstance().greetings()
    }

    private val _apiState = MutableStateFlow<ResponseResult<List<DrugEntity>>>(ResponseResult.None)
    val apiState = _apiState.asStateFlow()

    init {
        //to update greeting message
        updateGreeting()

        // get the dashboard data
        viewModelScope.launch {
            medicineRepository.getMedicineData().catch {
                _apiState.value = ResponseResult.Error(it.message ?: "")
            }.onEach {
                _apiState.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun getEmailId() = sharedPrefManager.getEmail()?:""
}