package com.vladshtuka.explainer.presentation.screen_start_game.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vladshtuka.explainer.databinding.TeamScoreRadioButtonItemBinding
import com.vladshtuka.explainer.domain.model.Team
import com.vladshtuka.explainer.presentation.screen_start_game.viewmodel.StartGameViewModel

class TeamScoreAdapter(
    private val viewModel: StartGameViewModel
) : ListAdapter<Team, TeamScoreAdapter.TeamScoreViewHolder>(TeamDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamScoreViewHolder {
        return TeamScoreViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TeamScoreViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.getRadioButton().isChecked = lastSelectedPosition == position
        holder.getRadioButton().setOnClickListener {
            if (lastSelectedPosition >= 0) {
                notifyItemChanged(lastSelectedPosition)
            }
            lastSelectedPosition = position
            notifyItemChanged(lastSelectedPosition)
            viewModel.setTeam(getItem(position))
        }
    }

    class TeamScoreViewHolder constructor(private val binding: TeamScoreRadioButtonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(team: Team) {
            binding.team = team
            val text = team.name + " = " + team.score
            binding.teamScoreRadioButton.text = text
        }

        fun getRadioButton(): RadioButton {
            return binding.teamScoreRadioButton
        }

        companion object {

            fun from(parent: ViewGroup): TeamScoreViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TeamScoreRadioButtonItemBinding.inflate(layoutInflater, parent, false)
                return TeamScoreViewHolder(binding)
            }
        }

    }

    companion object {
        var lastSelectedPosition = -1
    }
}

class TeamDiffCallback : DiffUtil.ItemCallback<Team>() {
    override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
        return oldItem == newItem
    }
}