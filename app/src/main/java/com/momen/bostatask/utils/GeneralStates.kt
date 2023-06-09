package com.momen.bostatask.utils

sealed class GeneralStates {
    object Loading : GeneralStates()
    object Success : GeneralStates()
    data class Error(val message: String?) : GeneralStates()
    object NoConnect : GeneralStates()
}