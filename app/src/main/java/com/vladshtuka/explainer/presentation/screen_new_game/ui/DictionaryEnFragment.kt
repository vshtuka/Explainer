package com.vladshtuka.explainer.presentation.screen_new_game.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.vladshtuka.explainer.R
import com.vladshtuka.explainer.common.Constants
import com.vladshtuka.explainer.databinding.FragmentDictionaryEnBinding
import com.vladshtuka.explainer.presentation.screen_new_game.adapter.DictionaryAdapter
import com.vladshtuka.explainer.presentation.screen_new_game.adapter.DictionaryListener
import com.vladshtuka.explainer.presentation.screen_new_game.viewmodel.NewGameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DictionaryEnFragment : Fragment() {

    private lateinit var binding: FragmentDictionaryEnBinding
    private val viewModel: NewGameViewModel by activityViewModels()
    private lateinit var dictionaryAdapter: DictionaryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_dictionary_en, container, false
        )
        initRecyclerView()
        setUpObservers()
        viewModel.getDictionariesFromJson(Constants.DICTIONARY_EN)

        return binding.root
    }

    private fun setUpObservers() {
        viewModel.dictionaryEnList.observe(viewLifecycleOwner) { dictionaryEnList ->
            dictionaryAdapter.submitList(dictionaryEnList)
        }
    }

    private fun initRecyclerView() {
        dictionaryAdapter = DictionaryAdapter(DictionaryListener { dictionary ->
            viewModel.setDictionary(dictionary)
            viewModel.setFullDictionary(dictionary)
            viewModel.getDictionaryName()
            val dialogFragment = requireParentFragment() as DialogFragment
            dialogFragment.dismiss()
        })
        binding.dictionaryRecyclerViewEn.adapter = dictionaryAdapter
        binding.dictionaryRecyclerViewEn.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

}