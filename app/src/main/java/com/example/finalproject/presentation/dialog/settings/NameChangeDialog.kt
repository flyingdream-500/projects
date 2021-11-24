package com.example.finalproject.presentation.dialog.settings

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.finalproject.databinding.DialogChangeNameLayoutBinding
import com.example.finalproject.model.user.User
import com.example.finalproject.utils.constants.DefaultConstants.DEFAULT_USER_NAME
import com.example.finalproject.utils.constants.DefaultConstants.DEFAULT_USER_SURNAME


/**
 * Диалоговое окно изменения данных о пользователе
 */
class NameChangeDialog(
    private var currentUser: User?,
    private val saveUser: (User) -> Unit
) : DialogFragment() {

    private var _binding: DialogChangeNameLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogChangeNameLayoutBinding.inflate(LayoutInflater.from(context))

        currentUser?.let { user ->

            initNameFields(user)

            binding.buttonOk.setOnClickListener {
                user.name = binding.inputName.text.toString()
                user.surname = binding.inputSurname.text.toString()

                if (userNameIsEmpty()) {
                    user.setDefaultUserName()
                }
                saveUser.invoke(user)
                dialog?.cancel()
            }
        }

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initNameFields(user: User) {
        binding.inputName.setText(user.name)
        binding.inputSurname.setText(user.surname)
    }

    private fun User.setDefaultUserName() {
        name = DEFAULT_USER_NAME
        surname = DEFAULT_USER_SURNAME
    }

    private fun userNameIsEmpty() =
        binding.inputName.text.isNullOrEmpty() or
                binding.inputSurname.text.isNullOrEmpty()

}