package com.vladshtuka.explainer.presentation.screen_start_game.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vladshtuka.explainer.R
import com.vladshtuka.explainer.databinding.FragmentStartGameBinding
import com.vladshtuka.explainer.presentation.screen_start_game.viewmodel.StartGameViewModel

class StartGameFragment : Fragment() {

    private lateinit var binding: FragmentStartGameBinding
    private val viewModel: StartGameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_start_game, container, false
        )
        setNavigationButton()
        setStartGameButton()
        setUpObservers()

        return binding.root
    }

    private fun setNavigationButton() {
        binding.startGameToolbar.setNavigationOnClickListener {
            this.findNavController()
                .navigate(StartGameFragmentDirections.actionStartGameFragmentToNewGameFragment())
        }
    }

    private fun setStartGameButton() {
        binding.startGameStartGameButton.setOnClickListener {
            this.findNavController()
                .navigate(StartGameFragmentDirections.actionStartGameFragmentToGameFragment())
        }
    }

    private fun setUpObservers() {
        viewModel.teamsList.observe(viewLifecycleOwner) { teamsList ->
            for (team in teamsList) {
                val radioButton = layoutInflater.inflate(R.layout.radio_button, null) as RadioButton
                val text = team.name + " = " + team.score
                radioButton.text = text
                radioButton.setOnClickListener {
                    binding.startGameTeam.text = team.name
                    viewModel.setTeam(team)
                }
                binding.startGameRadioGroup.addView(radioButton)
            }
        }
    }

}