package org.quickness.interfaces

import org.quickness.data.model.DataFirestore

interface FirebaseFirestore {
    suspend fun getData(): DataFirestore
    suspend fun updateField(fieldName: String, value: Any)
}