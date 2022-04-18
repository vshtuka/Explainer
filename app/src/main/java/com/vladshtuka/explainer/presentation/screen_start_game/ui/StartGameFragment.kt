package com.vladshtuka.explainer.presentation.screen_start_game.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.vladshtuka.explainer.R
import com.vladshtuka.explainer.databinding.FragmentNewGameBinding
import com.vladshtuka.explainer.databinding.FragmentStartGameBinding
import com.vladshtuka.explainer.presentation.screen_new_game.adapter.TeamAdapter
import com.vladshtuka.explainer.presentation.screen_new_game.adapter.TeamListener
import com.vladshtuka.explainer.presentation.screen_new_game.ui.NewGameFragmentDirections
import com.vladshtuka.explainer.presentation.screen_new_game.viewmodel.NewGameViewModel
import com.vladshtuka.explainer.presentation.screen_start_game.adapter.TeamScoreAdapter
import com.vladshtuka.explainer.presentation.screen_start_game.adapter.TeamScoreListener
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
        initTeamScoreRecyclerView()
        setUpObservers()

        return binding.root
    }

    private fun setNavigationButton() {
        binding.startGameToolbar.setNavigationOnClickListener {
            this.findNavController()
                .navigate(StartGameFragmentDirections.actionStartGameFragmentToNewGameFragment())
        }
    }

    private fun initTeamScoreRecyclerView() {
        teamScoreAdapter = TeamScoreAdapter(TeamScoreListener { team ->
            binding.startGameTeam.text = team?.name
        })
        binding.startGameRecyclerView.adapter = teamScoreAdapter
        binding.startGameRecyclerView.addItemDecoration(
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