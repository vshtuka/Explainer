package com.vladshtuka.explainer.presentation.screen_finish_game.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vladshtuka.explainer.R
import com.vladshtuka.explainer.databinding.WordItemBinding
import com.vladshtuka.explainer.domain.model.Word

class WordAdapter(
    private val answerTrueListener: AnswerTrueListener,
    private val answerFalseListener: AnswerFalseListener,
) : ListAdapter<Word, WordAdapter.WordViewHolder>(WordDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(getItem(position)!!, answerTrueListener, answerFalseListener)
    }

    class WordViewHolder constructor(private val binding: WordItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            word: Word,
            answerTrueListener: AnswerTrueListener,
            answerFalseListener: AnswerFalseListener
        ) {
            binding.word = word
            binding.recyclerViewWord.text = word.word
            binding.answerTrueListener = answerTrueListener
            binding.answerFalseListener = answerFalseListener

            if (word.isAnswerTrue) {
                binding.recyclerViewTrueButton.setImageResource(R.drawable.check_circle)
                binding.recyclerViewFalseButton.setImageResource(R.drawable.outline_cancel)

            } else {
                binding.recyclerViewTrueButton.setImageResource(R.drawable.check_circle_outline)
                binding.recyclerViewFalseButton.setImageResource(R.drawable.cancel)

            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): WordViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WordItemBinding.inflate(layoutInflater, parent, false)
                return WordViewHolder(binding)
            }
        }

    }

}

class WordDiffCallback : DiffUtil.ItemCallback<Word>() {
    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem.word == newItem.word
    }

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem == newItem
    }
}

class AnswerTrueListener(val answerTrueListener: (word: Word?, answerTrue: ImageView?, answerFalse: ImageView?) -> Unit) {
    fun onClick(word: Word?, answerTrue: ImageView?, answerFalse: ImageView?) =
        answerTrueListener(word, answerTrue, answerFalse)
}

class AnswerFalseListener(val answerFalseListener: (word: Word?, answerFalse: ImageView?, answerTrue: ImageView?) -> Unit) {
    fun onClick(word: Word?, answerFalse: ImageView?, answerTrue: ImageView?) =
        answerFalseListener(word, answerFalse, answerTrue)
}