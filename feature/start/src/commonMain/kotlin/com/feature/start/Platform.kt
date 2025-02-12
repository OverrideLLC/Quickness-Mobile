package com.feature.start

expect fun platform(): String

fun hola() {
    println(
        "Hello, ${platform()}!"
    )
}