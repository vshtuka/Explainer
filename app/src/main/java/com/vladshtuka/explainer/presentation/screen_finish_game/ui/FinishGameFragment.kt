package com.vladshtuka.explainer.presentation.screen_finish_game.ui

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.vladshtuka.explainer.R
import com.vladshtuka.explainer.common.Constants
import com.vladshtuka.explainer.databinding.FragmentFinishGameBinding
import com.vladshtuka.explainer.domain.model.Word
import com.vladshtuka.explainer.presentation.screen_finish_game.adapter.AnswerFalseListener
import com.vladshtuka.explainer.presentation.screen_finish_game.adapter.WordAdapter
import com.vladshtuka.explainer.presentation.screen_finish_game.adapter.AnswerTrueListener
import com.vladshtuka.explainer.presentation.screen_finish_game.viewmodel.FinishGameViewModel
import com.vladshtuka.explainer.presentation.screen_game.ui.GameToHomeDialogFragment

class FinishGameFragment : Fragment() {

    private lateinit var binding: FragmentFinishGameBinding
    private lateinit var finishSound: MediaPlayer
    private val viewModel: FinishGameViewModel by activityViewModels()
    private lateinit var wordAdapter: WordAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_finish_game, container, false
        )
        val words = FinishGameFragmentArgs.fromBundle(requireArguments()).wordsList?.toList()
        setNavigationButton()
        handleBackPressButton()
        startFinishSoundMediaPlayer()
        initRecyclerView(words)
        initScore(words)
        setFinishGameButton()
        setUpObservers()

        return binding.root
    }

    private fun startFinishSoundMediaPlayer() {
        finishSound = MediaPlayer.create(requireContext(), R.raw.finish_round_sound)
        finishSound.start()
    }

    private fun initScore(words: List<Word>?) {
        viewModel.initScore(words)
    }

    private fun setNavigationButton() {
        viewModel.getTeamName()
        binding.finishGameToolbar.setNavigationOnClickListener {
            FinishToHomeDialogFragment().show(
                childFragmentManager,
                Constants.FINISH_GAME_TO_HOME_DIALOG_TAG
            )
        }
    }

    private fun handleBackPressButton() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                FinishToHomeDialogFragment().show(
                    childFragmentManager,
                    Constants.FINISH_GAME_TO_HOME_DIALOG_TAG
                )
            }
        })
    }

    private fun initRecyclerView(words: List<Word>?) {
        wordAdapter = WordAdapter(AnswerTrueListener { word, answerTrue, answerFalse ->
            if (word != null) {
                if (!word.isAnswerTrue) {
                    answerTrue!!.setImageResource(R.drawable.check_circle)
                    answerFalse!!.setImageResource(R.drawable.outline_cancel)
                    viewModel.addTwoPoints()
                    word.isAnswerTrue = true
                }
            }
        }, AnswerFalseListener { word, answerFalse, answerTrue ->
            if (word != null) {
                if (word.isAnswerTrue) {
                    answerFalse!!.setImageResource(R.drawable.cancel)
                    answerTrue!!.setImageResource(R.drawable.check_circle_outline)
                    viewModel.subtractTwoPoints()
                    word.isAnswerTrue = false
                }
            }
        })
        binding.finishGameRecyclerView.adapter = wordAdapter
        binding.finishGameRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        wordAdapter.submitList(words)
    }

    private fun setFinishGameButton() {
        binding.finishGameFinishGameButton.setOnClickListener {
            viewModel.updateTeamScore()
            viewModel.removeActiveTeam()
            this.findNavController()
                .navigate(FinishGameFragmentDirections.actionFinishGameFragmentToStartGameFragment())
        }
    }

    private fun setUpObservers() {
        viewModel.teamName.observe(viewLifecycleOwner) { teamName ->
            binding.finishGameToolbar.title = teamName
        }

        viewModel.gamePoints.observe(viewLifecycleOwner) { gamePoints ->
            binding.finishGamePoints.text = gamePoints.toString()
        }
    }

    override fun onStop() {
        super.onStop()
        finishSound.stop()
        finishSound.release()
    }

}