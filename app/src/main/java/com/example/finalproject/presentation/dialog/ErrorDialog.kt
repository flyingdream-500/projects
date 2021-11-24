package com.example.finalproject.presentation.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.finalproject.databinding.DialogErrorBinding
import com.example.finalproject.utils.extensions.TextViewExtensions.setErrorMessage

/**
 * Диалоговое окно для отображения ошибки при загрузке данных
 * Параметры:
 * @see message текст сообщения
 */
class ErrorDialog(private val message: String?, @DrawableRes private val errorDrawable: Int) :
    DialogFragment() {

    private var _binding: DialogErrorBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        _binding = DialogErrorBinding.inflate(LayoutInflater.from(requireContext()))

        binding.errorLogo.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                errorDrawable
            )
        )
        binding.errorMessage.setErrorMessage(message)

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setCancelable(true)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}