package com.vladshtuka.explainer.presentation.screen_new_game.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.vladshtuka.explainer.R
import com.vladshtuka.explainer.common.Constants
import com.vladshtuka.explainer.databinding.FragmentNewGameBinding
import com.vladshtuka.explainer.domain.model.Team
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
        handleBackPressButton()
        setDictionaryChoice()
        setTeamAddButton()
        setAddTeamEditTextDoneButton()
        setGameTime()
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

    private fun handleBackPressButton() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(NewGameFragmentDirections.actionNewGameFragmentToHomeFragment())
            }
        })
    }

    private fun setDictionaryChoice() {
        viewModel.getDictionaryName()
        binding.newGameDictionaryChoice.setOnClickListener {
            DictionaryDialogFragment().show(childFragmentManager, Constants.DICTIONARY_DIALOG_TAG)
        }
    }

    private fun setTeamAddButton() {
        binding.newGameAddTeamButton.setOnClickListener {
            addTeam()
        }
    }

    private fun setAddTeamEditTextDoneButton() {
        binding.newGameNewTeamEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addTeam()
            }
            false
        }
    }

    private fun addTeam() {
        viewModel.addTeam(
            Team(name = binding.newGameNewTeamEditText.text.toString())
        )
        binding.newGameNewTeamEditText.text.clear()
    }

    private fun setGameTime() {
        viewModel.setDefaultGameTime()
        binding.newGameTimePlusButton.setOnClickListener {
            viewModel.addMinute()
        }
        binding.newGameTimeMinusButton.setOnClickListener {
            viewModel.subtractMinute()
        }
    }

    private fun initTeamsRecyclerView() {
        teamAdapter = TeamAdapter(TeamListener { team ->
            viewModel.deleteTeam(team!!.id!!)
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
            if (viewModel.isDictionaryChosen() && viewModel.isTeamAdded()) {
                viewModel.setGameCreated()
                this.findNavController()
                    .navigate(NewGameFragmentDirections.actionNewGameFragmentToStartGameFragment())
            } else if (!viewModel.isDictionaryChosen()) {
                Toast.makeText(requireContext(), getString(R.string.please_choose_dictionary), Toast.LENGTH_SHORT)
                    .show()
            } else if (!viewModel.isTeamAdded()) {
                Toast.makeText(requireContext(), getString(R.string.please_add_team), Toast.LENGTH_SHORT)
                    .show()
            }
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