package com.example.deeplinkapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.deeplinkapp.model.StackQuestions
import com.example.deeplinkapp.network.NetworkService.getService
import com.example.deeplinkapp.utils.idFromPath
import kotlinx.android.synthetic.main.activity_link.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Активити для получения данных по ссылке:
 *  Принимает ссылки с вопросами с сайта stackoverflow.com
 *  Вытаскивает с адреса id вопроса
 *  Делает запрос в API для получения заголовка
 *  Отображает заголовок вопроса в TextView
 */
class LinkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_link)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        intent?.data?.path?.let {
            val id = it.idFromPath()
            loadStackById(id)
        }
    }

    /**
     * Запрос в сеть и отображение полученных данных
     */
    private fun loadStackById(id: String) {
        getService().loadQuestion(id).enqueue(
            object : Callback<StackQuestions> {
                override fun onResponse(
                    call: Call<StackQuestions>,
                    response: Response<StackQuestions>
                ) {
                    if (response.isSuccessful) {
                        val questions = response.body()?.questions ?: emptyList()
                        if (questions.isNotEmpty()) {
                            val stack = questions[0]
                            tv_link_title.text = stack.title
                        }
                    }
                }

                override fun onFailure(call: Call<StackQuestions>, t: Throwable) {
                    tv_link_title.apply {
                        text = String.format(resources.getString(R.string.link_error), t.message)
                        setTextColor(ContextCompat.getColor(context, R.color.red))
                    }
                }
            }
        )
    }



}