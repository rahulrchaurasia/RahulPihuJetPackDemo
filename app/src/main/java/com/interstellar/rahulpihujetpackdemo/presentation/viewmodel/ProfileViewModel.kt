package com.interstellar.rahulpihujetpackdemo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.interstellar.rahulpihujetpackdemo.rootGraph.navigation.AppDataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val appDataManager: AppDataManager
): ViewModel() {

    fun logout() = appDataManager.logout()
}