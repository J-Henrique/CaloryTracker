package com.jhbb.onboarding_presentation.nutrient_goal

sealed class NutrientGoalEvent {
    data class OnCarbRationEnter(val ratio: String): NutrientGoalEvent()
    data class OnProteinRationEnter(val protein: String): NutrientGoalEvent()
    data class OnFatRationEnter(val fat: String): NutrientGoalEvent()
    object OnNextClick: NutrientGoalEvent()
}
