package com.vladshtuka.explainer.presentation.screen_game.ui

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vladshtuka.explainer.R
import com.vladshtuka.explainer.common.Constants
import com.vladshtuka.explainer.databinding.FragmentGameBinding
import com.vladshtuka.explainer.domain.model.Word
import com.vladshtuka.explainer.presentation.screen_game.viewmodel.GameViewModel
import kotlin.math.roundToInt

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private val viewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game, container, false
        )
        setNavigationButton()
        handleBackPressButton()
        setWordCard()
        setTrueAnswer()
        setFalseAnswer()
        startTimer()
        setUpObservers()

        return binding.root
    }

    private fun setNavigationButton() {
        viewModel.getTeamName()
        binding.gameToolbar.setNavigationOnClickListener {
            GameToHomeDialogFragment().show(childFragmentManager, Constants.GAME_TO_HOME_DIALOG_TAG)
        }
    }

    private fun handleBackPressButton() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                GameToHomeDialogFragment().show(
                    childFragmentManager,
                    Constants.GAME_TO_HOME_DIALOG_TAG
                )
            }
        })
    }

    private fun setWordCard() {
        viewModel.getDictionary()
        binding.gameWordText.text = viewModel.getRandomWord()
    }

    private fun setTrueAnswer() {
        binding.gameTrueAnswerLayout.setOnClickListener {
            val trueAnswerCount = binding.gameTrueAnswerCount.text.toString().toInt() + 1
            binding.gameTrueAnswerCount.text = trueAnswerCount.toString()
            viewModel.addWordToList(Word(binding.gameWordText.text.toString(), true))
            binding.gameWordText.text = viewModel.getRandomWord()
        }
    }

    private fun setFalseAnswer() {
        binding.gameFalseAnswerLayout.setOnClickListener {
            val falseAnswerCount = binding.gameFalseAnswerCount.text.toString().toInt() + 1
            binding.gameFalseAnswerCount.text = falseAnswerCount.toString()
            viewModel.addWordToList(Word(binding.gameWordText.text.toString(), false))
            binding.gameWordText.text = viewModel.getRandomWord()
        }
    }

    private fun startTimer() {
        viewModel.getTime()
    }

    private fun setUpObservers() {
        viewModel.teamName.observe(viewLifecycleOwner) { teamName ->
            binding.gameToolbar.title = teamName
        }

        viewModel.gameTime.observe(viewLifecycleOwner) { gameTime ->
            if (gameTime != null) {
                val timer = object : CountDownTimer(
                    Constants.ONE_MINUTE * gameTime,
                    Constants.ONE_SECOND
                ) {
                    override fun onTick(millisUntilFinished: Long) {
                        val secondsLeft =
                            (millisUntilFinished / Constants.ONE_SECOND.toDouble()).roundToInt()
                        binding.gameTimer.text = secondsLeft.toString()
                    }

                    override fun onFinish() {
                        findNavController().navigate(
                            GameFragmentDirections.actionGameFragmentToFinishGameFragment(
                                viewModel.getWordsList().toTypedArray()
                            )
                        )
                        viewModel.clearWordsList()
                    }

                }
                timer.start()
            }
        }
    }

}