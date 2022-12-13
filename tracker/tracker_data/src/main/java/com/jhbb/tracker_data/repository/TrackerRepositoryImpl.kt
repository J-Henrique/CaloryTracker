package com.jhbb.tracker_data.repository

import com.jhbb.tracker_data.local.TrackerDao
import com.jhbb.tracker_data.mapper.toTrackableFood
import com.jhbb.tracker_data.mapper.toTrackedFood
import com.jhbb.tracker_data.mapper.toTrackedFoodEntity
import com.jhbb.tracker_data.remote.OpenFoodApi
import com.jhbb.tracker_domain.model.TrackableFood
import com.jhbb.tracker_domain.model.TrackedFood
import com.jhbb.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val dao: TrackerDao,
    private val api: OpenFoodApi
): TrackerRepository {

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int,
    ): Result<List<TrackableFood>> {
        return try {
            val dto = api.searchFood(query, page, pageSize)
            Result.success(dto.products.mapNotNull { it.toTrackableFood() })
        } catch(e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        dao.insertTrackedFood(food.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        dao.deleteTrackedFood(food.toTrackedFoodEntity())
    }

    override fun getFoodForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return dao.getFoodsForDate(
            day = localDate.dayOfMonth,
            month = localDate.monthValue,
            year = localDate.year
        ).map { entities ->
            entities.map { it.toTrackedFood() }
        }
    }
}