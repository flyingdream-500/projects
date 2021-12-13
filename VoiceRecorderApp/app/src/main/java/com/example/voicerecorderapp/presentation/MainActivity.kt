package com.example.voicerecorderapp.presentation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.voicerecorderapp.R
import com.example.voicerecorderapp.databinding.ActivityMainBinding
import com.example.voicerecorderapp.presentation.viewpager.ViewPagerAdapter
import com.example.voicerecorderapp.presentation.viewpager.ViewPagerAdapter.Companion.LISTENING_ID
import com.example.voicerecorderapp.presentation.viewpager.ViewPagerAdapter.Companion.RECORDING_ID
import com.google.android.material.tabs.TabLayoutMediator


/**
 * Главное активити приложения
 */
class MainActivity : FragmentActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tabs by lazy {
        arrayListOf(
            resources.getString(R.string.recording_tab),
            resources.getString(R.string.listening_tab)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pager.adapter = ViewPagerAdapter(this)

        toCurrentFragment()

        TabLayoutMediator(
            binding.tabLayout, binding.pager
        ) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }


    /**
     * Обработка открытия активити из уведомления
     */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        toCurrentFragment()
    }

    /**
     * Переход к фрагменту в соответствии с нажатым уведомлением
     */
    private fun toCurrentFragment() {
        val fragmentNumber = intent?.action
        fragmentNumber?.let {
            when (it) {
                RECORDING_FRAGMENT_NUMBER -> {
                    binding.pager.setCurrentItem(RECORDING_ID, false)
                }
                LISTENING_FRAGMENT_NUMBER -> {
                    binding.pager.setCurrentItem(LISTENING_ID, false)
                }
            }
        }
    }

    companion object {
        const val RECORDING_FRAGMENT_NUMBER = "RECORDING_FRAGMENT"
        const val LISTENING_FRAGMENT_NUMBER = "LISTENING_FRAGMENT"
    }
}