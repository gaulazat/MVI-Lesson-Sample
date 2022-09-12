package com.android_course.mvi_lesson.mvi

sealed class FirstFragmentAction {
    object ButtonClick : FirstFragmentAction()
    class OnLoadSuccess(val result: String) : FirstFragmentAction()
    class OnLoadError(val error: String) : FirstFragmentAction()

}