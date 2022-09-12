package com.android_course.mvi_lesson.mvi

data class FirstFragmentState(
    val isLoading: Boolean = false,
    val error: String? = "success",
    val success: String? = null
)