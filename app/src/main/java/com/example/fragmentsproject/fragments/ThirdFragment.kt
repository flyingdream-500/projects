package com.example.fragmentsproject.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.fragmentsproject.R
import com.example.fragmentsproject.databinding.ThirdFragmentBinding
import com.example.fragmentsproject.interfaces.PublicApi


class ThirdFragment : Fragment() {

    private lateinit var binding: ThirdFragmentBinding
    private lateinit var publicApi: PublicApi
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            publicApi = activity as PublicApi
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.third_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //binding = ThirdFragmentBinding.bind(view)
        //binding.tvMessage.text = arguments?.getString(TEXT_KEY)
        textView = view.findViewById<TextView>(R.id.tv_message)

        if (savedInstanceState == null) {
            Log.d("TAGG", "ThirdFragment: args ${arguments?.getString(TEXT_KEY)}")
            textView.text = arguments?.getString(TEXT_KEY)
        } else {
            Log.d("TAGG", "ThirdFragment: saveState ${savedInstanceState.getString("qwerty")}")
            textView.text = savedInstanceState.getString("qwerty")
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("qwerty", textView.text.toString())
    }

    fun setText(text: String) {
        Log.d("TAGG", "ThirdFragment: setText $text")
        //binding?.tvMessage?.text = text
        textView.text = text
    }

    companion object {
        const val TEXT_KEY = "TEXT_KEY"

        fun newInstance(text: String): ThirdFragment {
            return ThirdFragment().apply {
                arguments = bundleOf(TEXT_KEY to text)
            }
        }
    }
}