package com.example.deeplinkapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.deeplinkapp.utils.KEY_DATE
import com.example.deeplinkapp.utils.KEY_NAME
import com.example.deeplinkapp.utils.KEY_SURNAME
import kotlinx.android.synthetic.main.activity_deep_link.*

/**
 * Активити для принятия данных по DeepLink
 */
class DeepLinkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deep_link)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        intent?.data?.let {
            val name = it.getQueryParameter(KEY_NAME)
            val surname = it.getQueryParameter(KEY_SURNAME)
            val date = it.getQueryParameter(KEY_DATE)

            tv_info.text = String.format(resources.getString(R.string.deep_link_info), name, surname, date)
        }

    }
}