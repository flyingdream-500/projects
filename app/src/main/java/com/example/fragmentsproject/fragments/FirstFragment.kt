package com.example.fragmentsproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.fragmentsproject.interfaces.PublicApi
import com.example.fragmentsproject.R
import com.example.fragmentsproject.databinding.FirstFragmentBinding


class FirstFragment : Fragment() {

    private lateinit var publicApi: PublicApi
    private lateinit var binding: FirstFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        publicApi = activity as PublicApi

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.first_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FirstFragmentBinding.bind(view)
        binding.etMessage.addTextChangedListener { publicApi.setText(it.toString()) }
    }

    companion object {
        fun newInstance(): FirstFragment {
            return FirstFragment()
        }
    }
}