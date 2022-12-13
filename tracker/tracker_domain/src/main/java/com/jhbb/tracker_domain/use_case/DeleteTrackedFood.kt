package com.jhbb.tracker_domain.use_case

import com.jhbb.tracker_domain.model.TrackedFood
import com.jhbb.tracker_domain.repository.TrackerRepository

class DeleteTrackedFood(
    private val repository: TrackerRepository
) {

    suspend operator fun invoke(food: TrackedFood) {
        repository.deleteTrackedFood(food)
    }
}