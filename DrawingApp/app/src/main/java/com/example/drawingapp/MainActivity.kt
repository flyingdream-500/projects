package com.example.drawingapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.drawingapp.databinding.ActivityMainBinding
import com.example.drawingapp.utils.Extensions.clickListener
import com.example.drawingapp.utils.Extensions.initBadger

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomView.apply {
            initBadger()
            clickListener(binding.drawingView)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        binding.apply {
            when (item.itemId) {
                R.id.reset -> drawingView.reset()
                R.id.undo -> drawingView.undo()
                R.id.redo -> drawingView.redo()
            }
        }
        return true
    }

}