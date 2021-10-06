package com.example.fragmentsproject

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.fragmentsproject.databinding.ActivityMainBinding
import com.example.fragmentsproject.fragments.FirstFragment
import com.example.fragmentsproject.fragments.ThirdFragment
import com.example.fragmentsproject.fragments.ThirdFragment.Companion.newInstance
import com.example.fragmentsproject.interfaces.PublicApi

class MainActivity : AppCompatActivity(), PublicApi {

    private var thirdFragmentTAG = "ThirdFragment"
    private var thirdFragment: ThirdFragment? = null
    private var textT: String = ""

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            Log.d("TAGG", "MA: first fragment init")
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<FirstFragment>(R.id.first_fragment_container_view_tag)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("qs", textT)
    }


    override fun onClick() {
        /*if (getThirdFragmentByTag() == null) {
            val bundle = bundleOf(TEXT_KEY to textT)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<ThirdFragment>(R.id.third_fragment_container_view_tag, args = bundle, tag = thirdFragmentTAG)
            }
        }*/
        Log.d("TAGG", "MA: onClick")
        if (thirdFragment == null) {
            Log.d("TAGG", "MA: thirdTag is null")
            thirdFragment = newInstance(textT)
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.third_fragment_container_view_tag, thirdFragment!!, thirdFragmentTAG )
                .commit()
        }
    }

    fun getThirdFragmentByTag(): ThirdFragment? {
        return supportFragmentManager.findFragmentByTag(thirdFragmentTAG) as? ThirdFragment
    }

    override fun setText(text: String) {
        Log.d("TAGG", "MA: setText $text")
        textT = text
        //getThirdFragmentByTag()?.setText(text)
        thirdFragment?.setText(text)
    }

    override fun getText(): String {
        return textT
    }
}