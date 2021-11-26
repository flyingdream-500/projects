package com.example.finalproject.presentation.dialog.settings

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.finalproject.databinding.DialogChangeNameLayoutBinding
import com.example.finalproject.model.user.User
import com.example.finalproject.presentation.viewmodel.SettingsViewModel
import com.example.finalproject.utils.constants.DefaultConstants.DEFAULT_USER_NAME
import com.example.finalproject.utils.constants.DefaultConstants.DEFAULT_USER_SURNAME


/**
 * Диалоговое окно изменения данных о пользователе
 */
class NameChangeDialog : DialogFragment() {

    private var _binding: DialogChangeNameLayoutBinding? = null
    private val binding get() = _binding!!

    private val settingsViewModel: SettingsViewModel by viewModels(
        ownerProducer = {requireParentFragment().childFragmentManager.primaryNavigationFragment!!}
    )

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogChangeNameLayoutBinding.inflate(LayoutInflater.from(context))

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

            settingsViewModel.observeUser().observe(viewLifecycleOwner) { currentUser ->
                currentUser?.let { user ->
                    if (savedInstanceState == null) {
                        initNameFields(user.name, user.surname)
                    } else {
                        val name = savedInstanceState.getString(NAME_KEY) ?: user.name
                        val surname = savedInstanceState.getString(SURNAME_KEY) ?: user.surname
                        initNameFields(name, surname)
                    }

                    binding.buttonOk.setOnClickListener {

                        user.name = binding.inputName.text.toString()
                        user.surname = binding.inputSurname.text.toString()

                        if (userNameIsEmpty()) {
                            user.setDefaultUserName()
                        }
                        settingsViewModel.saveUser(user)
                        dialog?.cancel()
                    }
                }
            }

        return binding.root
    }


    /**
     * Сохранение введенных данных после поворота экрана
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(NAME_KEY, binding.inputName.text.toString())
        outState.putString(SURNAME_KEY, binding.inputSurname.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initNameFields(name: String, surname: String) {
        binding.inputName.setText(name)
        binding.inputSurname.setText(surname)
    }

    private fun User.setDefaultUserName() {
        name = DEFAULT_USER_NAME
        surname = DEFAULT_USER_SURNAME
    }

    private fun userNameIsEmpty() =
        binding.inputName.text.isNullOrEmpty() or
                binding.inputSurname.text.isNullOrEmpty()


    companion object {
        private const val NAME_KEY = "NAME"
        private const val SURNAME_KEY = "SURNAME"
    }


}