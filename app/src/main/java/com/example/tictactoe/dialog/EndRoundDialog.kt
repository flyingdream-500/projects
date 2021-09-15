package com.example.tictactoe.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.tictactoe.R
import kotlinx.android.synthetic.main.end_round_dialog.view.*

class EndRoundDialog(private val isMan: Boolean): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.end_round_dialog, null)
        if (isMan) {
            //Текст победителя
            view.tv_round_description.text = requireContext().resources.getString(R.string.round_win)
            //Изображение победителя
            Glide.with(view)
                .load(R.raw.won_round)
                .into(view.iv_round_result)
        } else {
            //Текст проигравшего
            view.tv_round_description.text = requireContext().resources.getString(R.string.round_lose)
            //Изображение проигравшего
            Glide.with(view)
                .load(R.raw.lose_round)
                .into(view.iv_round_result)
        }
        //Кнопка закрытия диалогового окна
        view.btn_cancel.setOnClickListener { dialog?.cancel()}

        return AlertDialog.Builder(requireContext())
            .setView(view)
            .show()
    }

}