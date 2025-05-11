package org.override.quickness.data.api.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Entity(tableName = "users")
data class UserLyra(
    @PrimaryKey(autoGenerate = true) val userId: Long = 0,
    val name: String,
    val birthDate: LocalDate,
    val gender: String,  // "Male", "Female", "Other"
    val height: Double,  // En cm
    val weight: Double, // En kg
    val activityLevel: String, // Sedentary, Light, Moderate, Active, Very Active
    val goal: String, // Lose Weight, Maintain, Gain Weight
    val dailyCalorieGoal: Int,
    val proteinGoal: Int,    // En gramos
    val carbGoal: Int,       // En gramos
    val fatGoal: Int,        // En gramos
    val waterGoal: Int       // En ml
)

@Entity(tableName = "foods")
data class Food(
    @PrimaryKey(autoGenerate = true) val foodId: Long = 0,
    val name: String,
    val brand: String?,
    val servingSize: Double, // En gramos
    val calories: Double,
    val protein: Double,
    val carbs: Double,
    val fat: Double,
    val fiber: Double,
    val sugar: Double,
    val sodium: Double,
    val isCustom: Boolean, // True para alimentos creados por el usuario
    val creationDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
)

@Entity(tableName = "meals")
data class Meal(
    @PrimaryKey(autoGenerate = true) val mealId: Long = 0,
    val name: String, // Desayuno, Almuerzo, Cena, Snack, etc.
    val time: LocalTime,
    val date: LocalDate
)

@Entity(
    tableName = "meal_food",
    primaryKeys = ["mealId", "foodId"],
    foreignKeys = [
        ForeignKey(
            entity = Meal::class,
            parentColumns = ["mealId"],
            childColumns = ["mealId"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Food::class,
            parentColumns = ["foodId"],
            childColumns = ["foodId"],
            onDelete = CASCADE
        )
    ]
)
data class MealFoodCrossRef(
    val mealId: Long,
    val foodId: Long,
    val quantity: Double // Multiplicador de la porción (ej: 1.5 porciones)
)

@Entity(tableName = "water_intake")
data class WaterIntake(
    @PrimaryKey(autoGenerate = true) val entryId: Long = 0,
    val date: LocalDate,
    val amount: Int, // En ml
    val time: LocalTime
)

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true) val exerciseId: Long = 0,
    val name: String,
    val duration: Int, // En minutos
    val caloriesBurned: Int,
    val date: LocalDate,
    val time: LocalTime,
    val type: String // Cardio, Strength, etc.
)

@Entity(tableName = "daily_progress")
data class DailyProgress(
    @PrimaryKey val date: LocalDate,
    val totalCalories: Int,
    val totalProtein: Int,
    val totalCarbs: Int,
    val totalFat: Int,
    val totalWater: Int,
    val totalExerciseCalories: Int
)