package com.vladshtuka.explainer.presentation.screen_home.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vladshtuka.explainer.R
import com.vladshtuka.explainer.presentation.screen_home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewGameDialogFragment : DialogFragment() {

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.new_game_question))
            .setMessage(getString(R.string.game_clear))
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                cleanGameInformation()
                this.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToNewGameFragment())
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                dialog?.dismiss()
            }
            .create()

    private fun cleanGameInformation() {
        viewModel.removeDictionary()
        viewModel.removeTeams()
        viewModel.removeActiveTeam()
        viewModel.setDefaultTime()
        viewModel.setGameDeleted()
    }

}