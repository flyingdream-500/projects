package com.example.fragmentsproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.fragmentsproject.R
import com.example.fragmentsproject.databinding.ThirdFragmentBinding
import com.example.fragmentsproject.interfaces.PublicApi


class ThirdFragment : Fragment() {

    private lateinit var publicApi: PublicApi
    private lateinit var binding: ThirdFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        publicApi = activity as PublicApi
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.third_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = ThirdFragmentBinding.bind(view)
        binding.tvMessage.text = arguments?.getString(TEXT_KEY)
    }

    fun setText(text: String) {
        binding.tvMessage.text = text
    }

    companion object {
        private const val TEXT_KEY = "TEXT_KEY"

        fun newInstance(text: String): ThirdFragment {
            return ThirdFragment().apply {
                arguments = bundleOf(TEXT_KEY to text)
            }
        }
    }
}