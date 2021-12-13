package com.example.contentproviderapp.presentation.navigation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.contentproviderapp.R
import com.example.contentproviderapp.appComponent
import com.example.contentproviderapp.databinding.FragmentDictionaryBinding
import com.example.contentproviderapp.presentation.dialog.ErrorDialog
import com.example.contentproviderapp.presentation.recyclerview.dictionary.DictionaryAdapter
import com.example.contentproviderapp.presentation.viewmodel.SharedViewModel
import com.example.contentproviderapp.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject


/**
 * Fragment for showing dictionary items
 */
class DictionaryFragment: Fragment() {

    private var _binding: FragmentDictionaryBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    // Lazy implementation of sharedViewModel by AndroidViewModelFactory
    private val sharedViewModel by activityViewModels<SharedViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        requireActivity().appComponent.dictionaryFragmentComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDictionaryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DictionaryAdapter(sharedViewModel::deleteDictionary)
        binding.rvWords.adapter = adapter

        sharedViewModel.dictionaryItemsErrorLiveData.observe(viewLifecycleOwner) { throwable ->
            throwable?.let {
                ErrorDialog(throwable.message).show(parentFragmentManager, "ERROR_FRAGMENT")
            }
        }

        sharedViewModel.dictionaryItemsProgressLiveData.observe(viewLifecycleOwner) { isProgress ->
            if (isProgress) {
                binding.loading.visibility = View.VISIBLE
            } else {
                binding.loading.visibility = View.GONE
            }
        }
        sharedViewModel.dictionaryItemsLiveData.observe(viewLifecycleOwner) { items ->
           if (items.isNullOrEmpty()) {
               showEmptyImage()
           } else {
               showDictionary()
           }
            adapter.submitList(items)

        }

        sharedViewModel.loadDataAsyncRx()

        binding.addWordFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_wordsFragment_to_addWordFragment)
        }
    }



    private fun showEmptyImage() {
        binding.rvWords.visibility = View.GONE
        binding.emptyImage.visibility = View.VISIBLE
        binding.emptyText.visibility = View.VISIBLE
    }

    private fun showDictionary() {
        binding.rvWords.visibility = View.VISIBLE
        binding.emptyImage.visibility = View.GONE
        binding.emptyText.visibility = View.GONE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}