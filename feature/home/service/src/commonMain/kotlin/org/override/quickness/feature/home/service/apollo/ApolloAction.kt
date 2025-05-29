package org.override.quickness.feature.home.service.apollo

import org.override.quickness.feature.home.service.apollo.utils.CourseData

sealed interface ApolloAction {
    data class OnCourseClick(val data: CourseData): ApolloAction
}