package com.android_course.mvi_lesson.mvi

import com.android_course.mvi_lesson.mvi.FirstFragmentAction.*
import com.freeletics.rxredux.Reducer

class FirstFragmentUpdate : Reducer<FirstFragmentState, FirstFragmentAction> {

    override fun invoke(
        currentState: FirstFragmentState,
        action: FirstFragmentAction
    ): FirstFragmentState {
        return when (action) {
            ButtonClick -> currentState.copy(isLoading = true, success = null, error = null)
            is OnLoadSuccess -> currentState.copy(isLoading = false, success = action.result)
            is OnLoadError -> {
                currentState.copy(isLoading = false, success = null, error = action.error)
            }
        }
    }
}