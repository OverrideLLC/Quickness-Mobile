package org.quickness.data.remote

import org.quickness.interfaces.FirebaseAuth
import org.quickness.data.model.AuthResult

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class FirebaseService(): FirebaseAuth {
    override suspend fun signIn(email: String, password: String): AuthResult?
}