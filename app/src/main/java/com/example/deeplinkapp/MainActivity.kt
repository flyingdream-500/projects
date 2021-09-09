package com.example.deeplinkapp

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import com.example.deeplinkapp.utils.KEY_DATE
import com.example.deeplinkapp.utils.KEY_NAME
import com.example.deeplinkapp.utils.KEY_SURNAME
import com.example.deeplinkapp.utils.formatter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * Начальная точка входа в приложение
 * В полях EditText вводим Имя, Фамилию и Дату Рождения,
 * таким образом создавая динамический DeepLink
 * По нажатию на ссылку откроется DeepLinkActivity с переданными данными
 */
class MainActivity : AppCompatActivity() {

    private var name = ""
    private var surname = ""
    private var date = ""
    private var dateCalendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateText()

        input_name.doOnTextChanged { text, _, _, _ ->
            name = text.toString()
            updateText()
        }
        input_surname.doOnTextChanged { text, _, _, _ ->
            surname = text.toString()
            updateText()
        }

        input_date.setOnClickListener {
            DatePickerDialog(
                this,
                getDatePickerListener(),
                dateCalendar.get(Calendar.YEAR),
                dateCalendar.get(Calendar.MONTH),
                dateCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        tv_deepLink.setOnClickListener {
            val deepLinkIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(tv_deepLink.text.toString())
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(deepLinkIntent)
        }

    }


    /**
     * Listener для диалогового окна выбора даты и обновления поля выбора даты
     */
    private fun getDatePickerListener(): DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { _, i, i2, i3 ->
            dateCalendar.set(i,i2,i3)
            date = formatter.format(dateCalendar.time)
            input_date.setText(date)
            updateText()
        }
    }

    /**
     * Динамическое заполнение deep link данными об:
     * Имени
     * Фамилии
     * Дате Рождения
     */
    private fun updateText() {
        tv_deepLink.text = String.format(resources.getString(R.string.main_deep_link),
            name, surname, date,
            KEY_NAME, KEY_SURNAME, KEY_DATE
        )
    }
}