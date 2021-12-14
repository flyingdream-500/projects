package com.example.fragmentsprojectii

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import com.example.fragmentsprojectii.Extensions.addFr
import com.example.fragmentsprojectii.Extensions.deleteFr
import com.example.fragmentsprojectii.Extensions.isAddSelected
import com.example.fragmentsprojectii.Extensions.isBackStackSelected
import com.example.fragmentsprojectii.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val stackLiveData = MutableLiveData<Int>()
    private val fragmentsLiveData = MutableLiveData<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLiveData()

        supportFragmentManager.changeCounter()

        binding.buttonAdd.setOnClickListener {
            supportFragmentManager.apply {
                addFr(binding.isBackStackSelected(), binding.isAddSelected())
                changeCounter()
            }
        }

        binding.buttonRemove.setOnClickListener {
            supportFragmentManager.apply {
                deleteFr(binding.isBackStackSelected())
                changeCounter()
            }
        }

    }

    private fun initLiveData() {
        stackLiveData.observe(this) {
            binding.stackedCountText.text =
                String.format(resources.getString(R.string.stacked_count), it)
        }
        fragmentsLiveData.observe(this) {
            binding.addedCountText.text =
                String.format(resources.getString(R.string.added_count), it)
        }
    }

    private fun FragmentManager.changeCounter() {
        stackLiveData.value = backStackEntryCount
        fragmentsLiveData.value = fragments.size
    }




}