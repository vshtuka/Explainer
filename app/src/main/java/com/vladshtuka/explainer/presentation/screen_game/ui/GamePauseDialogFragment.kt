package com.vladshtuka.explainer.presentation.screen_game.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.vladshtuka.explainer.R
import com.vladshtuka.explainer.presentation.screen_game.viewmodel.GameViewModel

class GamePauseDialogFragment : DialogFragment() {

    private val viewModel: GameViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.pause))
            .setPositiveButton(getString(R.string.continue_game)) { _, _ ->
                viewModel.startGame()
                dialog?.dismiss()
            }
            .create()

}