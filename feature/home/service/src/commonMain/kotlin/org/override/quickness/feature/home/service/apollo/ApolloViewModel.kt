package org.override.quickness.feature.home.service.apollo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.override.quickness.feature.home.service.apollo.utils.CourseData

class ApolloViewModel : ViewModel() {

    private val _state = MutableStateFlow(ApolloState())
    val state = _state
        .onStart { }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ApolloState()
        )

    fun onAction(action: ApolloAction) {
        when (action) {
            is ApolloAction.OnCourseClick -> onCourseClick(action.data)
        }
    }

    private fun onCourseClick(data: CourseData) {
        _state.update {
            it.copy(
                selectedCourse = data
            )
        }
    }

}