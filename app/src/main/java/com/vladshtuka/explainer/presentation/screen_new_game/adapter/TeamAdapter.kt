package com.vladshtuka.explainer.presentation.screen_new_game.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vladshtuka.explainer.databinding.TeamItemBinding
import com.vladshtuka.explainer.domain.model.Team

class TeamAdapter(
    private val teamListener: TeamListener,
) : ListAdapter<Team, TeamAdapter.TeamViewHolder>(TeamDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(getItem(position)!!, teamListener)
    }

    class TeamViewHolder constructor(private val binding: TeamItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(team: Team, teamListener: TeamListener) {
            binding.team = team
            binding.teamName.text = team.name
            binding.teamListener = teamListener
        }

        companion object {
            fun from(parent: ViewGroup): TeamViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TeamItemBinding.inflate(layoutInflater, parent, false)
                return TeamViewHolder(binding)
            }
        }

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

class TeamListener(val teamListener: (team: Team?) -> Unit) {
    fun onClick(team: Team?) = teamListener(team)
}