package com.plcoding.calorytracker

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.jhbb.core.domain.model.ActivityLevel
import com.jhbb.core.domain.model.Gender
import com.jhbb.core.domain.model.GoalType
import com.jhbb.core.domain.model.UserInfo
import com.jhbb.core.domain.preferences.Preferences
import com.jhbb.core.domain.use_case.FilterOutDigits
import com.jhbb.tracker_domain.use_case.*
import com.jhbb.tracker_presentation.search.SearchViewModel
import com.jhbb.tracker_presentation.tracker_overview.TrackerOverviewViewModel
import com.plcoding.calorytracker.repository.TrackerRepositoryFake
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@HiltAndroidTest
class TrackerOverviewE2E {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var repository: TrackerRepositoryFake
    private lateinit var trackerUseCases: TrackerUseCases
    private lateinit var preferences: Preferences
    private lateinit var trackerOverviewViewModel: TrackerOverviewViewModel
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var navController: NavHostController

    @Before
    fun setUp() {
        preferences = mockk(relaxed = true)
        every { preferences.loadUserInfo() } returns UserInfo(
            gender = Gender.Male,
            age = 20,
            weight = 80f,
            height = 180,
            activityLevel = ActivityLevel.Medium,
            goalType = GoalType.KeepWeight,
            carbRatio = 0.4f,
            proteinRatio = 0.3f,
            fatRatio = 0.3f
        )
        repository = TrackerRepositoryFake()
        trackerUseCases = TrackerUseCases(
            trackFood = TrackFood(repository),
            searchFood = SearchFood(repository),
            getFoodsForDate = GetFoodsForDate(repository),
            deleteTrackedFood = DeleteTrackedFood(repository),
            calculateMealNutrients = CalculateMealNutrients(preferences)
        )
        trackerOverviewViewModel = TrackerOverviewViewModel(
            preferences = preferences,
            trackerUseCases = trackerUseCases
        )
        searchViewModel = SearchViewModel(
            trackerUseCases = trackerUseCases,
            filterOutDigits = FilterOutDigits()
        )
    }
}