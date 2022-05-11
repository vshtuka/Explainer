package com.vladshtuka.explainer.presentation.screen_new_game.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vladshtuka.explainer.common.Constants
import com.vladshtuka.explainer.presentation.screen_new_game.ui.DictionaryEnFragment
import com.vladshtuka.explainer.presentation.screen_new_game.ui.DictionaryUaFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = Constants.DICTIONARIES_COUNT

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DictionaryEnFragment()
            else -> DictionaryUaFragment()
        }
    }
}