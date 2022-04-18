package com.vladshtuka.explainer.presentation.screen_new_game.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vladshtuka.explainer.databinding.DictionaryItemBinding
import com.vladshtuka.explainer.domain.model.Dictionary

class DictionaryAdapter(
    private val dictionaryListener: DictionaryListener,
) : ListAdapter<Dictionary, DictionaryAdapter.DictionaryViewHolder>(DictionaryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder {
        return DictionaryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        holder.bind(getItem(position)!!, dictionaryListener)
    }

    class DictionaryViewHolder constructor(private val binding: DictionaryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dictionary: Dictionary, dictionaryListener: DictionaryListener) {
            binding.dictionary = dictionary
            binding.recyclerViewDictionaryName.text = dictionary.name
            binding.recyclerViewDictionaryDescription.text = dictionary.description
            binding.recyclerViewDictionaryLanguage.text = dictionary.language
            binding.recyclerViewDictionaryWordsNumber.text = dictionary.wordsNumber
            binding.dictionaryListener = dictionaryListener
        }

        companion object {
            fun from(parent: ViewGroup): DictionaryViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DictionaryItemBinding.inflate(layoutInflater, parent, false)
                return DictionaryViewHolder(binding)
            }
        }

    }

}

class DictionaryDiffCallback : DiffUtil.ItemCallback<Dictionary>() {
    override fun areItemsTheSame(oldItem: Dictionary, newItem: Dictionary): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Dictionary, newItem: Dictionary): Boolean {
        return oldItem == newItem
    }
}

class DictionaryListener(val dictionaryListener: (dictionary: Dictionary?) -> Unit) {
    fun onClick(dictionary: Dictionary?) = dictionaryListener(dictionary)
}
