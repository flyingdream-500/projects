package com.example.voicerecorderapp

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.voicerecorderapp.databinding.ActivityMainBinding
import com.example.voicerecorderapp.presentation.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : FragmentActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tabs = arrayListOf(
        "Recording",
        "All Records"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPagerAdapter = ViewPagerAdapter(this)
        binding.pager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager
        ) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }
}