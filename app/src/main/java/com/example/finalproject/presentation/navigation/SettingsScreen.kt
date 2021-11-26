package com.example.finalproject.presentation.navigation

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentSettingsBinding
import com.example.finalproject.model.user.User
import com.example.finalproject.presentation.dialog.ErrorDialog
import com.example.finalproject.presentation.dialog.settings.NameChangeDialog
import com.example.finalproject.presentation.viewmodel.SettingsViewModel
import com.example.finalproject.utils.common.Settings.takePhotoIntent
import com.example.finalproject.utils.constants.DefaultConstants.AVATAR_FILE_NAME
import com.example.finalproject.utils.constants.FragmentConstants.CHANGE_NAME_TAG
import com.example.finalproject.utils.constants.FragmentConstants.DATABASE_ERROR_TAG
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.*


/**
 * Фрагмент настроек приложения
 */
@AndroidEntryPoint
class SettingsScreen : Fragment() {


    private val settingsViewModel: SettingsViewModel by viewModels()

    private var _binding: FragmentSettingsBinding? = null
    private val binding
        get() = _binding!!

    // Экземпляр класса пользователя
    private var currentUser: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUserData()
        initAuthentication()
        initNameField()
        initAvatarField()
    }


    /**
     * Метод инициализирует поле смены имени пользователя
     */
    private fun initNameField() {
        binding.settingsName.root.setOnClickListener {
            NameChangeDialog().show(parentFragmentManager, CHANGE_NAME_TAG)
        }
    }

    /**
     * Метод инициализирует поле установки аватара
     */
    private fun initAvatarField() {
        binding.settingsAvatar.root.setOnClickListener {
            startActivityForResult(takePhotoIntent(), GET_IMAGE_REQUEST_CODE)
        }
    }


    /**
     * Метод инициализирует поле аутентификации
     */
    private fun initAuthentication() {
        binding.settingsAuthentication.checkAuthentication.apply {
            isChecked = settingsViewModel.getSettingsAuth()
            setOnCheckedChangeListener  { _, b ->
                settingsViewModel.setSettingsAuth(b)
            }
        }
    }

    /**
     * Метод запрашивает данные по пользователю и подписывается на их изменения
     */
    private fun initUserData() {

        //наблюдение за данными пользователя
        settingsViewModel.observeUser().observe(viewLifecycleOwner) { user ->
            currentUser = user
            currentUser?.let {
                binding.settingsName.nameSummary.text = String.format(getString(R.string.user_name), it.name, it.surname)
                binding.settingsAvatar.avatarImage.setImageURI(Uri.parse(it.avatar))
            }
        }

        // наблюдение за ошибками
        settingsViewModel.observeUserError().observe(viewLifecycleOwner) { throwable ->
            throwable?.let {
                ErrorDialog(it.message, R.drawable.data_base_error).show(
                    parentFragmentManager,
                    DATABASE_ERROR_TAG
                )
            }
        }

        settingsViewModel.getUser()
    }

    /**
     * Получаем uri выбранного изображения и сохраняем:
     * - изображение для аватара в internal storage
     * - измененный user в БД
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GET_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                data?.data?.let { uri ->

                    val targetFile = getAvatarFile()

                    settingsViewModel.saveAvatarFile(uri, targetFile)

                    val newAvatarUri = Uri.fromFile(targetFile)
                    binding.settingsAvatar.avatarImage.setImageURI(uri)

                    currentUser?.let {
                        it.avatar = newAvatarUri.toString()
                        settingsViewModel.saveUser(it)
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * Файл размещения аватара в internal storage
     */
    private fun getAvatarFile(): File {
        val filePath = requireActivity().getExternalFilesDir(null)
        return File(filePath, AVATAR_FILE_NAME)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val GET_IMAGE_REQUEST_CODE = 121
    }

}