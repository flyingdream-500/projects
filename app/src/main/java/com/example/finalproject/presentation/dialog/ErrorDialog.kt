package com.example.finalproject.presentation.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.finalproject.R
import com.example.finalproject.databinding.DialogErrorBinding
import com.example.finalproject.utils.extensions.TextViewExtensions.setErrorMessage

/**
 * Диалоговое окно для отображения ошибки при загрузке данных
 * Параметры:
 * @see errorMessage текст сообщения
 * @see errorMessage изображение ошибки, может быть [R.drawable.network_error] или [R.drawable.data_base_error]
 */
class ErrorDialog(
    private val errorMessage: String? = "",
    @DrawableRes private val errorDrawable: Int = R.drawable.network_error
) : DialogFragment() {

    private var _binding: DialogErrorBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        _binding = DialogErrorBinding.inflate(LayoutInflater.from(requireContext()))

        if (savedInstanceState == null) {
            setErrorDrawable(errorDrawable)
            setErrorMessage(errorMessage)
        } else {
            val message = savedInstanceState.getString(MESSAGE_KEY)
            val drawableRes = savedInstanceState.getInt(DRAWABLE_KEY)
            setErrorDrawable(drawableRes)
            setErrorMessage(message)
        }


        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setCancelable(true)
            .show()
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(MESSAGE_KEY, errorMessage)
        outState.putInt(DRAWABLE_KEY, errorDrawable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setErrorDrawable(@DrawableRes drawableRes: Int) {
        binding.errorLogo.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                drawableRes
            )
        )
    }

    private fun setErrorMessage(message: String?) {
        binding.errorMessage.setErrorMessage(message)
    }


    companion object {
        private const val MESSAGE_KEY = "MESSAGE"
        private const val DRAWABLE_KEY = "DRAWABLE_RES"
    }


}