package com.vladshtuka.explainer.presentation.screen_new_game.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.vladshtuka.explainer.R
import com.vladshtuka.explainer.common.Constants
import com.vladshtuka.explainer.databinding.FragmentNewGameBinding
import com.vladshtuka.explainer.domain.model.Team
import com.vladshtuka.explainer.presentation.screen_home.ui.HomeFragmentDirections
import com.vladshtuka.explainer.presentation.screen_new_game.adapter.TeamAdapter
import com.vladshtuka.explainer.presentation.screen_new_game.adapter.TeamListener
import com.vladshtuka.explainer.presentation.screen_new_game.viewmodel.NewGameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewGameFragment : Fragment() {

    private lateinit var binding: FragmentNewGameBinding
    private val viewModel: NewGameViewModel by activityViewModels()
    private lateinit var teamAdapter: TeamAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_new_game, container, false
        )
        setNavigationButton()
        setDictionaryChoice()
        setTeamAddButton()
        setTimePlusButton()
        setTimeMinusButton()
        initTeamsRecyclerView()
        setStartGameButton()
        setUpObservers()

        return binding.root
    }

    private fun setNavigationButton() {
        binding.newGameToolbar.setNavigationOnClickListener {
            this.findNavController()
                .navigate(NewGameFragmentDirections.actionNewGameFragmentToHomeFragment())
        }
    }

    private fun setDictionaryChoice() {
        viewModel.getDictionaryName()
        binding.newGameDictionaryChoice.setOnClickListener {
            DictionaryDialogFragment().show(childFragmentManager, Constants.DICTIONARY_DIALOG_TAG)
        }
    }

    private fun setTeamAddButton() {
        binding.newGameAddTeamButton.setOnClickListener {
            viewModel.insertTeam(
                Team(
                    name = binding.newGameNewTeamEditText.text.toString(),
                    score = 0
                )
            )
        }
    }

    private fun setTimePlusButton() {
        binding.newGameTimePlusButton.setOnClickListener {
            viewModel.addMinute()
        }
    }

    private fun setTimeMinusButton() {
        binding.newGameTimeMinusButton.setOnClickListener {
            viewModel.subtractMinute()
        }
    }

    private fun initTeamsRecyclerView() {
        teamAdapter = TeamAdapter(TeamListener { team ->
            viewModel.deleteTeamById(team?.id)
        })
        binding.newGameTeamsRecyclerView.adapter = teamAdapter
        binding.newGameTeamsRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun setStartGameButton() {
        binding.newGameStartGameButton.setOnClickListener {
            this.findNavController()
                .navigate(NewGameFragmentDirections.actionNewGameFragmentToStartGameFragment())
        }
    }

    private fun setUpObservers() {
        viewModel.dictionaryName.observe(viewLifecycleOwner) { dictionaryName ->
            binding.newGameDictionaryChoice.text = dictionaryName
        }

        viewModel.teamsList.observe(viewLifecycleOwner) { teamsList ->
            teamAdapter.submitList(teamsList)
        }

        viewModel.gameTime.observe(viewLifecycleOwner) { gameTime ->
            binding.newGameTime.text = gameTime.toString()
        }
    }

}