package org.override.quickness.data.impl.repository

import org.override.quickness.data.api.repository.LyraRepository
import org.override.quickness.data.api.room.entity.Exercise
import org.override.quickness.data.api.room.entity.Food
import org.override.quickness.data.api.room.entity.MealFoodCrossRef
import org.override.quickness.data.api.room.entity.UserLyra
import org.override.quickness.data.api.room.pojos.DailySummary
import org.override.quickness.data.api.room.pojos.MealWithFoods
import kotlinx.datetime.LocalDate

class LyraRepositoryImpl: LyraRepository {
    override suspend fun insertUser(user: UserLyra) {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(): UserLyra? {
        TODO("Not yet implemented")
    }

    override suspend fun searchFoods(query: String): List<Food> {
        TODO("Not yet implemented")
    }

    override suspend fun insertFood(food: Food): Long {
        TODO("Not yet implemented")
    }

    override suspend fun getDailyMeals(date: LocalDate): List<MealWithFoods> {
        TODO("Not yet implemented")
    }

    override suspend fun insertMeal(meal: MealWithFoods): Long {
        TODO("Not yet implemented")
    }

    override suspend fun insertMealFoodCrossRef(crossRef: MealFoodCrossRef) {
        TODO("Not yet implemented")
    }

    override suspend fun getDailyWaterTotal(date: LocalDate): Int {
        TODO("Not yet implemented")
    }

    override suspend fun insertWaterEntry(amount: Int, date: LocalDate) {
        TODO("Not yet implemented")
    }

    override suspend fun getDailySummary(date: LocalDate): DailySummary {
        TODO("Not yet implemented")
    }

    override suspend fun insertExercise(exercise: Exercise) {
        TODO("Not yet implemented")
    }

    override suspend fun getDailyExerciseCalories(date: LocalDate): Int {
        TODO("Not yet implemented")
    }
}