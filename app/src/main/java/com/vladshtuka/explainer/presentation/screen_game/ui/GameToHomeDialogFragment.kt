package com.vladshtuka.explainer.presentation.screen_game.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vladshtuka.explainer.R
import com.vladshtuka.explainer.presentation.screen_home.ui.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameToHomeDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.exit_to_menu))
            .setMessage(getString(R.string.game_progress_lose))
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                this.findNavController()
                    .navigate(GameFragmentDirections.actionGameFragmentToHomeFragment())
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                dialog?.dismiss()
            }
            .create()

}