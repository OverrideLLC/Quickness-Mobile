package org.quickness

interface Platform {
    val name: String
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class Uri{
    fun navigate()
}