package com.data.impl.repository

import com.example.api.repository.LyraRepository
import com.example.api.room.entity.Exercise
import com.example.api.room.entity.Food
import com.example.api.room.entity.MealFoodCrossRef
import com.example.api.room.entity.UserLyra
import com.example.api.room.pojos.DailySummary
import com.example.api.room.pojos.MealWithFoods
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