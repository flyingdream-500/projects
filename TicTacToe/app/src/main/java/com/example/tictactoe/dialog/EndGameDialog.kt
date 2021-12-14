package com.example.tictactoe.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.tictactoe.R
import kotlinx.android.synthetic.main.end_game_dialog.view.*

class EndGameDialog(private val isMan: Boolean, private val clear: () -> Unit): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.end_game_dialog, null)

        if (isMan) {
            //Текст победителя
            view.tv_game_description.text = requireContext().resources.getString(R.string.game_win)
            //Гифка победителя
            Glide.with(view)
                .asGif()
                .load(R.raw.won_game)
                .into(view.iv_game_result)
        } else {
            //Текст проигравшего
            view.tv_game_description.text = requireContext().resources.getString(R.string.game_lose)
            //Гифка проигравшего
            Glide.with(view)
                .asGif()
                .load(R.raw.lose_game)
                .into(view.iv_game_result)
        }
        //Кнопка закрытия диалогового окна
        view.btn_new_game.setOnClickListener { dialog?.cancel() }

        return AlertDialog.Builder(requireContext())
            .setView(view)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        clear()
    }
}