package com.vladshtuka.explainer.presentation.screen_new_game.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.vladshtuka.explainer.R
import com.vladshtuka.explainer.databinding.FragmentDictionaryDialogBinding
import com.vladshtuka.explainer.presentation.screen_new_game.adapter.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DictionaryDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentDictionaryDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_dictionary_dialog, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabTitle = listOf("EN", "UA")
        binding.dictionaryViewPager.adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        TabLayoutMediator(
            binding.dictionaryTabLayout,
            binding.dictionaryViewPager
        ) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

}

