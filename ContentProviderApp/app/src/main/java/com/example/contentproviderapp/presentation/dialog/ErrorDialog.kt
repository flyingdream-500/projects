package com.example.contentproviderapp.presentation.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.contentproviderapp.databinding.DialogErrorBinding


/**
 * Error dialog for showing message
 */
class ErrorDialog(private val message: String?) : DialogFragment() {

    private var _binding: DialogErrorBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        _binding = DialogErrorBinding.inflate(LayoutInflater.from(requireContext()))

        binding.errorMessage.text = message

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