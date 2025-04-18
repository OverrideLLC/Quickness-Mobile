package com.example.api.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.api.room.entity.Exercise
import com.example.api.room.entity.Food
import com.example.api.room.entity.Meal
import com.example.api.room.entity.MealFoodCrossRef
import com.example.api.room.entity.UserLyra
import com.example.api.room.entity.WaterIntake
import com.example.api.room.pojos.DailySummary
import com.example.api.room.pojos.MealWithFoods
import kotlinx.datetime.LocalDate

@Dao
interface NutritionDao {
    // User Operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userLyra: UserLyra)

    @Query("SELECT * FROM users LIMIT 1")
    suspend fun getUser(): UserLyra?

    // Food Operations
    @Insert
    suspend fun insertFood(food: Food): Long

    @Query("SELECT * FROM foods WHERE name LIKE :query")
    suspend fun searchFoods(query: String): List<Food>

    // Meal Operations
    @Transaction
    @Query("SELECT * FROM meals WHERE date = :date")
    suspend fun getDailyMeals(date: LocalDate): List<MealWithFoods>

    @Insert
    suspend fun insertMeal(meal: Meal): Long

    @Insert
    suspend fun insertMealFoodCrossRef(crossRef: MealFoodCrossRef)

    // Water Operations
    @Query("SELECT SUM(amount) FROM water_intake WHERE date = :date")
    suspend fun getDailyWaterTotal(date: LocalDate): Int

    @Insert
    suspend fun insertWaterEntry(entry: WaterIntake)

    // Progress Operations
    @Transaction
    suspend fun getDailySummary(date: LocalDate): DailySummary {
        val user = getUser() ?: throw Exception("User not found")
        val meals = getDailyMeals(date)

        val totalCalories =
            meals.sumOf { meal -> meal.foods.sumOf { food -> (food.food.calories * food.quantity).toInt() } }
        val totalProtein =
            meals.sumOf { meal -> meal.foods.sumOf { food -> (food.food.protein * food.quantity).toInt() } }
        val totalCarbs =
            meals.sumOf { meal -> meal.foods.sumOf { food -> (food.food.carbs * food.quantity).toInt() } }
        val totalFat =
            meals.sumOf { meal -> meal.foods.sumOf { food -> (food.food.fat * food.quantity).toInt() } }

        return DailySummary(
            date = date,
            totalCalories = totalCalories,
            totalProtein = totalProtein,
            totalCarbs = totalCarbs,
            totalFat = totalFat,
            totalWater = getDailyWaterTotal(date),
            remainingCalories = user.dailyCalorieGoal - totalCalories
        )
    }

    // Exercise Operations
    @Query("SELECT SUM(caloriesBurned) FROM exercises WHERE date = :date")
    suspend fun getDailyExerciseCalories(date: LocalDate): Int

    @Insert
    suspend fun insertExercise(exercise: Exercise)
}