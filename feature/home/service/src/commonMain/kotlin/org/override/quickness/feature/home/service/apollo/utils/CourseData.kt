package org.override.quickness.feature.home.service.apollo.utils

data class CourseData(
    val title: String,
    val degree: String,
    val career: String,
    val section: String,
    val works: List<WorkData>,
    val announcements: List<AnnouncementData>
)
