package org.quickness

interface Platform {
    val name: String
}

expect class Uri{
    fun navigate()
}