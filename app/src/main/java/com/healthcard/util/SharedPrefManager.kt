package com.healthcard.util

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SharedPrefManager @Inject constructor(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setUserLoggedIn(loggedIn: Boolean) {
        sharedPreferences.edit().putBoolean(PREF_IS_LOGGED_IN, loggedIn).apply()
    }

    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(PREF_IS_LOGGED_IN, false)
    }

    fun getEmail(): String? {
        return sharedPreferences.getString(PREF_EMAIL, null)
    }

    fun saveEmail(email: String) {
        sharedPreferences.edit().putString(PREF_EMAIL, email).apply()
    }

    companion object{
        private const val PREF_NAME = "user_prefs"
        private const val PREF_IS_LOGGED_IN = "logged_in"
        private const val PREF_EMAIL = "email"
    }
}