package com.vladshtuka.explainer.presentation.screen_rules

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.vladshtuka.explainer.R
import com.vladshtuka.explainer.databinding.FragmentHomeBinding
import com.vladshtuka.explainer.databinding.FragmentRulesBinding

class RulesFragment : Fragment() {

    private lateinit var binding: FragmentRulesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_rules, container, false
        )
        setNavigationButton()

        return binding.root
    }

    private fun setNavigationButton() {
        binding.rulesToolbar.setNavigationOnClickListener {
            this.findNavController()
                .navigate(RulesFragmentDirections.actionRulesFragmentToHomeFragment())
        }
    }

}