package com.jhbb.tracker_data.local

import androidx.room.*
import com.jhbb.tracker_data.local.entity.TrackedFoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackedFood(foodEntity: TrackedFoodEntity)

    @Delete
    suspend fun deleteTrackedFood(foodEntity: TrackedFoodEntity)

    @Query(
        """
        SELECT *
        FROM trackedfoodentity
        WHERE dayOfMonth = :day AND month = :month AND year = :year
        """
    )
    fun getFoodsForDate(day: Int, month: Int, year: Int): Flow<List<TrackedFoodEntity>>
}