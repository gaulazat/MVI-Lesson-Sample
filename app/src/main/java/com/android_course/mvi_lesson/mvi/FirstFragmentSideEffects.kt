package com.android_course.mvi_lesson.mvi

import com.freeletics.rxredux.SideEffect
import io.reactivex.Single
import io.reactivex.rxkotlin.ofType
import java.util.concurrent.TimeUnit

class FirstFragmentSideEffects{

    val sideEffects = listOf(
        loadSideEffect()
    )

    private fun loadSideEffect(): SideEffect<FirstFragmentState, FirstFragmentAction> {
        return { actions, state ->
            actions.ofType<FirstFragmentAction.ButtonClick>()
                .flatMapSingle {
                    Single.timer(3, TimeUnit.SECONDS)
                        .map { "ready" }
                        .map<FirstFragmentAction> { FirstFragmentAction.OnLoadSuccess(it) }
                        .onErrorReturn { FirstFragmentAction.OnLoadError(it.message.orEmpty()) }
                }
        }
    }
}