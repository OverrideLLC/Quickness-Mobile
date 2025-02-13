rootProject.name = "quickness"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")
include(":feature")
include("start")
include(":data")
include("start")
include(":feature:start")
include(":data:datastore")
include(":data:room")
include(":services")
include(":network")
include(":shared")
include(":shared:ui")
include(":shared:utils")
include(":recurces")
include(":services:lyra")
