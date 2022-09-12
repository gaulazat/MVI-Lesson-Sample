package com.android_course.mvi_lesson

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android_course.mvi_lesson.databinding.FragmentFirstBinding
import com.android_course.mvi_lesson.mvi.FirstFragmentAction
import com.android_course.mvi_lesson.mvi.FirstFragmentSideEffects
import com.android_course.mvi_lesson.mvi.FirstFragmentState
import com.android_course.mvi_lesson.mvi.FirstFragmentUpdate
import com.freeletics.rxredux.reduxStore
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.BehaviorSubject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val actions = BehaviorSubject.create<FirstFragmentAction>()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun render(state: FirstFragmentState) {
        when {
            state.isLoading -> binding.textviewFirst.text = "loading"
            state.success != null -> binding.textviewFirst.text = state.success
            state.error != null -> binding.textviewFirst.text = state.error
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        compositeDisposable += actions
            .reduxStore(
                FirstFragmentState(),
                FirstFragmentSideEffects().sideEffects,
                FirstFragmentUpdate()
            )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { state -> render(state) }
        binding.buttonFirst.setOnClickListener {
            actions.onNext(FirstFragmentAction.ButtonClick)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        compositeDisposable.clear()
    }
}