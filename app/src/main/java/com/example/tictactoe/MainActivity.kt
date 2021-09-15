package com.example.tictactoe

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.tictactoe.dialog.EndGameDialog
import com.example.tictactoe.dialog.EndRoundDialog
import com.example.tictactoe.model.*
import com.example.tictactoe.utils.Const.COL_COUNT
import com.example.tictactoe.utils.Const.END_GAME_TAG
import com.example.tictactoe.utils.Const.END_ROUND_TAG
import com.example.tictactoe.utils.Const.ROW_COUNT
import com.example.tictactoe.utils.Extensions.gone
import com.example.tictactoe.utils.Extensions.refresh
import com.example.tictactoe.utils.Extensions.visible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Array<Button>>
    private val game = Game()

    private val manData = MutableLiveData<Man>(Man(0,0))
    private val robotData = MutableLiveData<Robot>(Robot(0,0))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        move_robot.gone()

        buttons = initButtons()
        game.start()

        observeCounter()

    }

    /**
        Наблюдение за счетчиком выигранных партий робота и человека
     */
    private fun observeCounter() {
        manData.observe(this) {
            man_counter.text = it.count.toString()
            man_image.setImageLevel(it.level)
        }

        robotData.observe(this) {
            robot_counter.text = it.count.toString()
            robot_image.setImageLevel(it.level)
        }
    }

    /**
        Инициализация массива кнопок и добавление слушателей
     */
    private fun initButtons(): Array<Array<Button>> {
        val btns = arrayOf(
            btn1,
            btn2,
            btn3,
            btn4,
            btn5,
            btn6,
            btn7,
            btn8,
            btn9
        )
        var i = 0
        return Array(ROW_COUNT) { row ->
                Array(COL_COUNT) { col ->
                    val b = btns[i++]
                    b.setOnClickListener(ButtonListener(row, col))
                    b
                }
            }
    }


    /**
        Слушатель нажатия кнопки с последующей логикой проверки победителя
        + ход компьютера с задержкой 1 сек
     */
    inner class ButtonListener(private val x: Int, private val y: Int): View.OnClickListener {
        override fun onClick(view: View?) {
            val button = view as Button
            var player = game.getCurrentPlayer()

            //Ход человека
            if (player?.isMan() == true) {
                //Ход человека
                if (game.makeTurn(x, y)) {
                    button.text = player.name
                }
                var winner = game.checkWinner()
                if (winner != null) {
                    gameOver(winner)
                    return
                }
                if (game.isFieldFilled()) {
                    gameOver()
                    return
                }
                move_robot.visible()
                move_man.gone()

                //Ход компьютера
                Handler(Looper.getMainLooper()).postDelayed( {
                    player = game.getCurrentPlayer()
                    if (player?.isMan() == false) {
                        var b = false
                        var row = 0
                        var col = 0
                        while (!b) {
                            row = (0 until ROW_COUNT).random()
                            col = (0 until COL_COUNT).random()
                            b = game.makeTurn(row, col)
                        }
                        buttons[row][col].text = player?.name
                        winner = game.checkWinner()
                        if (winner != null) {
                            gameOver(winner!!)
                        }
                        if (game.isFieldFilled()) {
                            gameOver()
                        }
                        move_man.visible()
                        move_robot.gone()
                    }
                }, 1_000)
            }


        }

    }
    /**
        Конец игры или раунда с победителем, "окрашивание в те цвета, в которые  он себя окрасил",
            показ диалогового окна с изображением(конец раунда) или с гифкой(конец игры)
     */
    private fun gameOver(winner: Player) {
        val isMan = winner.isMan()
        if (isMan) {
            val man = manData.value!!
            if (++man.count == 3) {
                man.level += 3_300
                manData.value = man
                EndGameDialog(isMan, ::clearCount).show(supportFragmentManager, END_GAME_TAG)
            } else {
                man.level += 3_300
                manData.value = man
                EndRoundDialog(isMan).show(supportFragmentManager, END_ROUND_TAG)
            }
        } else {
            val robot = robotData.value!!
            if (++robot.count == 3) {
                robot.level += 3_300
                robotData.value = robot
                EndGameDialog(isMan, ::clearCount).show(supportFragmentManager, END_GAME_TAG)
            } else {
                robot.level += 3_300
                robotData.value = robot
                EndRoundDialog(isMan).show(supportFragmentManager, END_ROUND_TAG)
            }
        }
        game.reset()
        buttons.refresh()

    }

    /**
        Конец раунда без победителя
     */
    private fun gameOver() {
        Toast.makeText(this, resources.getString(R.string.without_winner), Toast.LENGTH_SHORT).show()
        game.reset()
        buttons.refresh()
    }

    /**
        Очищение полей для начала новой игры
     */
    private fun clearCount() {
        manData.value = Man(0, 0)
        robotData.value = Robot(0, 0)
        move_man.visible()
        move_robot.gone()
    }

}