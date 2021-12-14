package com.example.fragmentsproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fragmentsproject.databinding.ActivityMainBinding
import com.example.fragmentsproject.fragments.ThirdFragment
import com.example.fragmentsproject.interfaces.PublicApi
import com.example.fragmentsproject.utils.createNewFragmentContainer

class MainActivity : AppCompatActivity(), PublicApi {

    private var thirdFragment: ThirdFragment? = null
    private lateinit var binding: ActivityMainBinding

    //Текст из первого фрагмента с EditText
    private var currentText: String = ""

    //Был ли совершен вызов третьего фрагмента
    private var clicked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(CURRENT_TEXT_KEY, currentText)
        outState.putBoolean(CLICKED_KEY, clicked)
    }

    //Восстановление значений введенного текста из первого фрагмента
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        clicked = savedInstanceState.getBoolean(CLICKED_KEY)
        currentText = savedInstanceState.getString(CURRENT_TEXT_KEY).toString()

        if (clicked) createThirdFragment()
    }


    override fun onClick() {
        if (!clicked) {
            clicked = true
            createThirdFragment()
        }
    }

    override fun setText(text: String) {
        currentText = text
        thirdFragment?.setText(text)
    }

    override fun getText(): String {
        return currentText
    }

    private fun createThirdFragment() {
        binding.createNewFragmentContainer()

        if (thirdFragment == null) {
            thirdFragment = ThirdFragment.newInstance(currentText)
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.third_fragment_container_view_tag, thirdFragment!!)
                .commit()
        }
    }

    companion object {
        private const val CLICKED_KEY = "CLICKED_KEY"
        private const val CURRENT_TEXT_KEY = "CURRENT_TEXT_KEY"
    }
}