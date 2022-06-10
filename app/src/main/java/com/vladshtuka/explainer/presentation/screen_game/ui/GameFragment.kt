package com.vladshtuka.explainer.presentation.screen_game.ui

import android.media.MediaPlayer
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
    private lateinit var timer: CountDownTimer
    private lateinit var trueSound: MediaPlayer
    private lateinit var falseSound: MediaPlayer
    private lateinit var backgroundMusic: MediaPlayer
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
        setPauseButton()
        startGame()
        setUpObservers()

        return binding.root
    }

    private fun createMediaPlayers() {
        trueSound = MediaPlayer.create(requireContext(), R.raw.true_sound)
        falseSound = MediaPlayer.create(requireContext(), R.raw.false_sound)
        backgroundMusic = MediaPlayer.create(requireContext(), R.raw.background_music)
        backgroundMusic.isLooping = true
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
            trueSound.start()
            val trueAnswerCount = binding.gameTrueAnswerCount.text.toString().toInt() + 1
            binding.gameTrueAnswerCount.text = trueAnswerCount.toString()
            viewModel.addWordToList(Word(binding.gameWordText.text.toString(), true))
            binding.gameWordText.text = viewModel.getRandomWord()
        }
    }

    private fun setFalseAnswer() {
        binding.gameFalseAnswerLayout.setOnClickListener {
            falseSound.start()
            val falseAnswerCount = binding.gameFalseAnswerCount.text.toString().toInt() + 1
            binding.gameFalseAnswerCount.text = falseAnswerCount.toString()
            viewModel.addWordToList(Word(binding.gameWordText.text.toString(), false))
            binding.gameWordText.text = viewModel.getRandomWord()
        }
    }


    private fun setPauseButton() {
        binding.gamePauseButton.setOnClickListener {
            viewModel.cancelGame()
            GamePauseDialogFragment().show(
                childFragmentManager,
                Constants.GAME_PAUSE_TAG
            )
        }
    }

    private fun startGame() {
        val gameTime = viewModel.getTime()
        viewModel.setTimeRemaining(Constants.ONE_MINUTE * gameTime)
        viewModel.startGame()
    }

    private fun setUpObservers() {
        viewModel.teamName.observe(viewLifecycleOwner) { teamName ->
            binding.gameToolbar.title = teamName
        }

        viewModel.isGameActive.observe(viewLifecycleOwner) { isGameActive ->
            if (isGameActive != null) {
                if (isGameActive) {
                    startTimer()
                } else {
                    timer.cancel()
                }
            }
        }
    }

    private fun startTimer() {
        val timeRemaining = viewModel.getTimeRemaining()
        timer = object : CountDownTimer(
            timeRemaining,
            Constants.ONE_SECOND
        ) {
            override fun onTick(millisUntilFinished: Long) {
                viewModel.setTimeRemaining(millisUntilFinished)
                val secondsLeft =
                    (millisUntilFinished / Constants.ONE_SECOND.toDouble()).roundToInt()
                binding.gameTimer.text = secondsLeft.toString()
            }

            override fun onFinish() {
                if (findNavController().currentDestination?.id == R.id.gameFragment) {
                    viewModel.addWordToList(
                        Word(
                            binding.gameWordText.text.toString(),
                            false
                        )
                    )
                    findNavController().navigate(
                        GameFragmentDirections.actionGameFragmentToFinishGameFragment(
                            viewModel.getWordsList().toTypedArray()
                        )
                    )
                    viewModel.finishGame()
                    viewModel.clearWordsList()
                }
            }

        }.start()
    }

    private fun startBackgroundMusic() {
        backgroundMusic.start()
    }

    override fun onStart() {
        super.onStart()
        createMediaPlayers()
        startBackgroundMusic()
    }

    override fun onStop() {
        super.onStop()
        trueSound.stop()
        falseSound.stop()
        backgroundMusic.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        trueSound.release()
        falseSound.release()
        backgroundMusic.release()
    }
}