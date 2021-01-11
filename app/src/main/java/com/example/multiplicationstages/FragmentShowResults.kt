package com.example.multiplicationstages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.multiplicationstages.databinding.FragmentLevelResultsBinding

class FragmentShowResults : Fragment() {
    private lateinit var binding : FragmentLevelResultsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLevelResultsBinding.inflate(inflater, container, false)
        populateScreenInfo()
        return binding.root
    }

    private fun populateScreenInfo() {
        binding.levelResults.text = resources.getString(R.string.level_results, Session.fragmentIdentifier)
        val percentage = ((Session.correctAnswers.toFloat() / 10.00) * 100.00).toInt()
        binding.percentScored.text = resources.getString(R.string.percentage_scored, "$percentage%")
        if (percentage >= 90) {
            if (Session.fragmentIdentifier == 1) {
                binding.messageAlert.text = resources.getString(R.string.congratulation)
                binding.conditionalButton.text = resources.getString(R.string.next_level)
                binding.conditionalButton.setOnClickListener {
                    Navigation.findNavController(binding.root).navigate(R.id.navigate_to_second_level)
                }
            } else {
                binding.conditionalButton.text = resources.getString(R.string.start_over)
                binding.messageAlert.text = resources.getString(R.string.levels_finished)
                binding.conditionalButton.setOnClickListener {
                    Navigation.findNavController(binding.root).navigate(R.id.navigate_to_first_level)
                }
            }
        } else {
            if (Session.fragmentIdentifier == 2) {
                Session.levelAttemptsLeft--
                binding.levelResults.text = resources.getString(R.string.attempts_left, Session.levelAttemptsLeft)
                if (Session.levelAttemptsLeft > 0) {
                    binding.messageAlert.text = resources.getString(R.string.alert_message)
                    binding.conditionalButton.text = resources.getString(R.string.try_again)
                    binding.conditionalButton.setOnClickListener {
                        Session.fragmentIdentifier = 0
                        Navigation.findNavController(binding.root).navigate(R.id.navigate_to_second_level)
                    }
                } else {
                    Session.levelAttemptsLeft = 3
                    binding.messageAlert.text = resources.getString(R.string.previous_level)
                    //binding.conditionalButton.text = resources.getString(R.string.try_again)
                    //binding.conditionalButton.setOnClickListener {
                        //Navigation.findNavController(binding.root).navigate(R.id.navigate_to_first_level)
                    //}
                }
            } else {
                binding.messageAlert.text = resources.getString(R.string.alert_message)
                binding.conditionalButton.text = resources.getString(R.string.try_again)
                binding.conditionalButton.setOnClickListener {
                    Navigation.findNavController(binding.root).navigate(R.id.navigate_to_first_level)
                }
            }
        }
        Session.correctAnswers = 0
    }
}