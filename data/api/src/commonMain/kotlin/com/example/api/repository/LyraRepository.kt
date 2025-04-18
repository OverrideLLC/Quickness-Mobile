package com.example.api.repository

import com.example.api.room.entity.Exercise
import com.example.api.room.entity.Food
import com.example.api.room.entity.MealFoodCrossRef
import com.example.api.room.entity.UserLyra
import com.example.api.room.pojos.DailySummary
import com.example.api.room.pojos.MealWithFoods
import kotlinx.datetime.LocalDate

interface LyraRepository {
    suspend fun insertUser(user: UserLyra)
    suspend fun getUser(): UserLyra?
    suspend fun searchFoods(query: String): List<Food>
    suspend fun insertFood(food: Food): Long
    suspend fun getDailyMeals(date: LocalDate): List<MealWithFoods>
    suspend fun insertMeal(meal: MealWithFoods): Long
    suspend fun insertMealFoodCrossRef(crossRef: MealFoodCrossRef)
    suspend fun getDailyWaterTotal(date: LocalDate): Int
    suspend fun insertWaterEntry(amount: Int, date: LocalDate)
    suspend fun getDailySummary(date: LocalDate): DailySummary
    suspend fun insertExercise(exercise: Exercise)
    suspend fun getDailyExerciseCalories(date: LocalDate): Int
}