package org.override.quickness.data.api.room.pojos

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import org.override.quickness.data.api.room.entity.Food
import org.override.quickness.data.api.room.entity.Meal
import org.override.quickness.data.api.room.entity.MealFoodCrossRef
import kotlinx.datetime.LocalDate

data class MealWithFoods(
    @Embedded val meal: Meal,
    @Relation(
        parentColumn = "mealId",
        entityColumn = "foodId",
        associateBy = Junction(MealFoodCrossRef::class)
    )
    val foods: List<FoodWithQuantity>
)

data class FoodWithQuantity(
    @Embedded val food: Food,
    val quantity: Double
)

data class DailySummary(
    val date: LocalDate,
    val totalCalories: Int,
    val totalProtein: Int,
    val totalCarbs: Int,
    val totalFat: Int,
    val totalWater: Int,
    val remainingCalories: Int
)