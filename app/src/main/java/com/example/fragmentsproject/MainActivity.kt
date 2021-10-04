package com.example.fragmentsproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.fragmentsproject.databinding.ActivityMainBinding
import com.example.fragmentsproject.fragments.FirstFragment
import com.example.fragmentsproject.fragments.ThirdFragment
import com.example.fragmentsproject.fragments.ThirdFragment.Companion.TEXT_KEY
import com.example.fragmentsproject.interfaces.PublicApi

class MainActivity : AppCompatActivity(), PublicApi {

    private var thirdFragmentTAG = "ThirdFragment"
    private var textT: String = ""

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<FirstFragment>(R.id.first_fragment_container_view_tag)
            }
        }
    }

    override fun onClick() {
        if (getThirdFragmentByTag() == null) {
            val bundle = bundleOf(TEXT_KEY to textT)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<ThirdFragment>(R.id.third_fragment_container_view_tag, args = bundle, tag = thirdFragmentTAG)
            }

        }
    }

    fun getThirdFragmentByTag(): ThirdFragment? {
        return supportFragmentManager.findFragmentByTag(thirdFragmentTAG) as? ThirdFragment
    }

    override fun setText(text: String) {
        textT = text
        getThirdFragmentByTag()?.setText(text)
    }

    override fun getText(): String {
        return ""
    }
}