package com.vladshtuka.explainer.presentation.screen_start_game.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.vladshtuka.explainer.R
import com.vladshtuka.explainer.databinding.FragmentStartGameBinding
import com.vladshtuka.explainer.presentation.screen_start_game.adapter.TeamScoreAdapter
import com.vladshtuka.explainer.presentation.screen_start_game.viewmodel.StartGameViewModel

class StartGameFragment : Fragment() {

    private lateinit var binding: FragmentStartGameBinding
    private val viewModel: StartGameViewModel by activityViewModels()
    private lateinit var teamScoreAdapter: TeamScoreAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_start_game, container, false
        )
        setNavigationButton()
        handleBackPressButton()
        setStartGameButton()
        initTeamScoreRecyclerView()
        setUpObservers()

        return binding.root
    }

    private fun setNavigationButton() {
        binding.startGameToolbar.setNavigationOnClickListener {
            this.findNavController()
                .navigate(StartGameFragmentDirections.actionStartGameFragmentToHomeFragment())
        }
    }

    private fun handleBackPressButton() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController()
                    .navigate(StartGameFragmentDirections.actionStartGameFragmentToHomeFragment())
            }
        })
    }

    private fun setStartGameButton() {
        binding.startGameStartGameButton.setOnClickListener {
            if (viewModel.isTeamChosen()) {
                teamScoreAdapter.lastSelectedPosition = -1
                this.findNavController()
                    .navigate(StartGameFragmentDirections.actionStartGameFragmentToGameFragment())
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.please_choose_team),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initTeamScoreRecyclerView() {
        teamScoreAdapter = TeamScoreAdapter(viewModel)
        binding.startGameTeamScoreRecyclerView.adapter = teamScoreAdapter
        binding.startGameTeamScoreRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun setUpObservers() {
        viewModel.teamsList.observe(viewLifecycleOwner) { teamsList ->
            teamScoreAdapter.submitList(teamsList)
        }
    }

}