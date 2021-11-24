package com.example.finalproject.presentation.navigation

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import com.example.finalproject.utils.common.Settings.saveAvatarToExternalFilesDir
import com.example.finalproject.utils.common.Settings.takePhotoIntent
import com.example.finalproject.utils.constants.SettingsConstants.AVATAR_FILE_NAME
import com.example.finalproject.utils.constants.SettingsConstants.CHANGE_NAME_TAG
import com.example.finalproject.utils.constants.SettingsConstants.GET_IMAGE_REQUEST_CODE
import com.example.finalproject.utils.constants.UrlConstants
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


/**
 * Фрагмент настроек приложения
 */
@AndroidEntryPoint
class SettingsScreen : Fragment() {

    private val settingsViewModel: SettingsViewModel by viewModels()


    private var _binding: FragmentSettingsBinding? = null
    private val binding
        get() = _binding!!

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

        settingsViewModel.observeUser().observe(viewLifecycleOwner) { user ->
            currentUser = user
            currentUser?.let {
                Log.d("TAGGG", "observe: ${it.avatar.toString()}")
                binding.settingsName.nameSummary.text = "${it.name} ${it.surname}"
                binding.settingsAvatar.avatarImage.setImageURI(Uri.parse(it.avatar))
            }
        }

        settingsViewModel.observeUserError().observe(viewLifecycleOwner) { throwable ->
            throwable?.let {
                ErrorDialog(it.message, R.drawable.data_base_error).show(
                    parentFragmentManager,
                    UrlConstants.DATABASE_ERROR_TAG
                )
            }
        }

        binding.settingsAuthentication.checkAuthentication.isChecked = settingsViewModel.getSettingsAuth()


        binding.settingsName.root.setOnClickListener {
            NameChangeDialog(
                currentUser,
                settingsViewModel::saveUser
            ).show(parentFragmentManager, CHANGE_NAME_TAG)
        }

        binding.settingsAuthentication.checkAuthentication.setOnCheckedChangeListener  { _, b ->
            settingsViewModel.setSettingsAuth(b)
        }

        binding.settingsAvatar.root.setOnClickListener {
            startActivityForResult(takePhotoIntent(), GET_IMAGE_REQUEST_CODE)
        }

        settingsViewModel.getUser()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GET_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                data?.data?.let { uri ->
                    val filePath = requireActivity().getExternalFilesDir(null)
                    val targetFile = File(filePath, AVATAR_FILE_NAME)

                    settingsViewModel.saveAvatarFile(uri, targetFile)
                    val newAvatarUri = Uri.fromFile(targetFile)
                    binding.settingsAvatar.avatarImage.setImageURI(uri)
                    //binding.settingsAvatar.avatarImage.setImageURI(Uri.parse(newAvatarUri.toString()))
                    currentUser?.let {
                        it.avatar = newAvatarUri.toString()
                        settingsViewModel.saveUser(it)
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}