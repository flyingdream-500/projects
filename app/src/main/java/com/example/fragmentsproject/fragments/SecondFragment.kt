package com.example.fragmentsproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragmentsproject.R
import com.example.fragmentsproject.databinding.SecondFragmentBinding
import com.example.fragmentsproject.interfaces.PublicApi


class SecondFragment : Fragment() {

    private lateinit var binding: SecondFragmentBinding
    private lateinit var publicApi: PublicApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        publicApi = activity as PublicApi
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.second_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = SecondFragmentBinding.bind(view)
        binding.buttonSend.setOnClickListener {
            publicApi.onClick()
        }
    }

    companion object {
        fun newInstance(): SecondFragment {
            return SecondFragment()
        }
    }
}