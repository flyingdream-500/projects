package com.example.contentproviderapp.presentation.navigation

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.contentproviderapp.R
import com.example.contentproviderapp.appComponent
import com.example.contentproviderapp.databinding.FragmentAddDictionaryItemBinding
import com.example.contentproviderapp.domain.model.DictionaryItem
import com.example.contentproviderapp.presentation.viewmodel.SharedViewModel
import com.example.contentproviderapp.presentation.viewmodel.ViewModelFactory
import com.example.contentproviderapp.utils.setInputFilters
import javax.inject.Inject

/**
 * Fragment for input keyword, translate and pick image
 */
class AddDictionaryItemFragment : Fragment() {

    private var _binding: FragmentAddDictionaryItemBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    // Lazy implementation of sharedViewModel by AndroidViewModelFactory
    private val sharedViewModel by activityViewModels<SharedViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        requireActivity().appComponent.addItemFragmentComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddDictionaryItemBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.run {
            selectedImageLiveData.observe(viewLifecycleOwner) { uri ->
                binding.logo.setImageURI(uri)
            }
            completeAddingDictionaryItemsLiveData.observe(viewLifecycleOwner) { isSuccessful ->
                if (isSuccessful) updateAfterAdding()
            }
        }

        binding.run {
            pickImage.setOnClickListener {
                PickImageFragment().show(parentFragmentManager, "GALLERY_DIALOG")
            }

            keywordInput.setInputFilters()
            translateInput.setInputFilters()

            addToDictionary.setOnClickListener {
                checkAndAddDictionaryItem()
            }
        }
    }

    /**
     * Function for adding dictionary item
     */
    private fun checkAndAddDictionaryItem() {
        val keyword = getKeyword()
        val translate = getTranslate()
        if (keyword.isNotBlank() && translate.isNotBlank()) {
            sharedViewModel.addToDictionary(
                DictionaryItem(
                    keyword = keyword,
                    translation = translate,
                    logo = getLogo()
                )
            )
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.fill_fields_warning),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getKeyword() =
        binding.keywordInput.text.toString().trim()

    private fun getTranslate() =
        binding.translateInput.text.toString().trim()

    private fun getLogo() =
        sharedViewModel.selectedImageLiveData.value.toString()

    /**
     * Update UI after adding Dictionary item
     */
    private fun updateAfterAdding() {
        binding.run {
            keywordInput.text?.clear()
            keywordInput.clearFocus()
            translateInput.text?.clear()
            translateInput.clearFocus()
        }
        sharedViewModel.pickImage(Uri.EMPTY)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}