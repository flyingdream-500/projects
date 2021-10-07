package com.example.fragmentsprojectii

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.fragmentsprojectii.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMainBinding.bind(view)
        binding.fragmentCounter.text = arguments?.getString(TEXT_COUNT) ?: "0"
    }

    companion object {
        const val TEXT_COUNT = "TEXT_COUNT"
        fun newInstance(text: String): MainFragment {
            return MainFragment().also {
                it.arguments = bundleOf(TEXT_COUNT to text)
            }
        }
    }
}