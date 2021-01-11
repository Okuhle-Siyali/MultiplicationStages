package com.example.multiplicationstages

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.multiplicationstages.databinding.FragmentFirstMultiplicationLevelBinding


class FragmentSecondMultiplicationLevel : Fragment() {
    private lateinit var binding : FragmentFirstMultiplicationLevelBinding
    var presenter = PresenterMainActivity()
    private lateinit var timer : CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFirstMultiplicationLevelBinding.inflate(inflater, container, false)
        binding.levelOne.text = resources.getString(R.string.level, 2)
        multiplicationInit()
        userInputListener()
        return binding.root
    }

    private fun submitAnswerButtonListener() {
        if (binding.userAnswer.text.isEmpty()) return
        binding.submitAnswer.setOnClickListener {
            presenter.attempts++
            timer.cancel()
            val answer = presenter.multiplicand * presenter.multiplier
            if (binding.userAnswer.text.toString() == answer.toString()) {
                presenter.correctAnswer++
            }
            if (presenter.attempts > 10) {
                Session.correctAnswers = presenter.correctAnswer
                Session.fragmentIdentifier = 2
                presenter.attempts = 1
                Navigation.findNavController(binding.root).navigate(R.id.navigate_to_results_fragment)
                return@setOnClickListener
            }
            binding.showProgress.text = resources.getString(R.string.progress_indicator, presenter.attempts)
            setMultiplicationValues()
            implementTimer()
            binding.userAnswer.text.clear()
            binding.submitAnswer.isEnabled = false
        }

    }

    private fun userInputListener() {
        binding.userAnswer.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrBlank()) {
                    binding.submitAnswer.isEnabled = true
                    submitAnswerButtonListener()
                }
                else binding.submitAnswer.isEnabled = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun implementTimer() {
        timer = object: CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.showTimeRemaining.text = resources.getString(R.string.time_alert, millisUntilFinished/1000)
            }

            override fun onFinish() {
                binding.userAnswer.text.clear()
                presenter.attempts++
                multiplicationInit()
                if (presenter.attempts > 10) {
                    Session.correctAnswers = presenter.correctAnswer
                    Session.fragmentIdentifier = 2
                    presenter.attempts = 1
                    Navigation.findNavController(binding.root).navigate(R.id.navigate_to_results_fragment)
                    return
                }
            }
        }
        timer.start()
    }

    private fun setMultiplicationValues() {
        presenter.generateRandomNumberLimit(11, 20)
        binding.showExpression.text = resources.getString(R.string.multiplication_expression, presenter.multiplicand, presenter.multiplier)
    }

    private fun multiplicationInit() {
        binding.submitAnswer.isEnabled = false
        setMultiplicationValues()
        implementTimer()
        binding.showProgress.text = resources.getString(R.string.progress_indicator, presenter.attempts)
    }
}