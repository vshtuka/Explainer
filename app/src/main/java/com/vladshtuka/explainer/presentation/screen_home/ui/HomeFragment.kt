package com.vladshtuka.explainer.presentation.screen_home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vladshtuka.explainer.R
import com.vladshtuka.explainer.common.Constants
import com.vladshtuka.explainer.databinding.FragmentHomeBinding
import com.vladshtuka.explainer.presentation.screen_home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home, container, false
        )
        setContinueButton()
        setNewGameButton()
        setRulesButton()
        setUpObservers()

        return binding.root
    }

    private fun setContinueButton() {
        viewModel.getGameCreatedState()
        binding.homePageContinueButton.setOnClickListener {
            this.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToStartGameFragment())
        }
    }

    private fun setNewGameButton() {
        binding.homePageNewGameButton.setOnClickListener {
            NewGameDialogFragment().show(childFragmentManager, Constants.NEW_GAME_DIALOG_TAG)
        }
    }

    private fun setRulesButton() {
        binding.homePageRulesButton.setOnClickListener {
            this.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToRulesFragment())
        }
    }

    private fun setUpObservers() {
        viewModel.isGameCreated.observe(viewLifecycleOwner) { isGameCreated ->
            if (isGameCreated != null) {
                if (isGameCreated) {
                    binding.homePageContinueButton.visibility = View.VISIBLE
                } else {
                    binding.homePageContinueButton.visibility = View.INVISIBLE
                }
            }
        }
    }

}