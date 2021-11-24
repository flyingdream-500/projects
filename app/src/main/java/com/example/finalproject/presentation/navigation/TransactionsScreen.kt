package com.example.finalproject.presentation.navigation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentTransactionsScreenBinding
import com.example.finalproject.presentation.dialog.ErrorDialog
import com.example.finalproject.presentation.recyclerview.transactions.TransactionsAdapter
import com.example.finalproject.presentation.viewmodel.TransactionsViewModel
import com.example.finalproject.utils.constants.UrlConstants.DATABASE_ERROR_TAG
import com.example.finalproject.utils.extensions.RecyclerViewExtensions.addVerticalDivider
import dagger.hilt.android.AndroidEntryPoint

/**
 * Фрагмент отображения данных о выполненных операциях
 */
@AndroidEntryPoint
class TransactionsScreen : Fragment() {

    private var _binding: FragmentTransactionsScreenBinding? = null
    private val binding
        get() = _binding!!


    private val transactionsViewModel: TransactionsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionsScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TransactionsAdapter()
        binding.rvOperations.apply {
            this.adapter = adapter
            addVerticalDivider(requireContext())
        }

        transactionsViewModel.run {

            observeTransactions().observe(viewLifecycleOwner) { transactions ->
                Log.d("INFO", transactions.size.toString())
                if(transactions.isEmpty()) {
                    hideTransactions()
                } else {
                    showTransactions()
                    adapter.setCurrencyList(transactions)
                }
            }

            observeTransactionsProgress().observe(viewLifecycleOwner) { inProgress ->
                if (inProgress) {
                    showProgress()
                } else {
                    hideProgress()
                }
            }

            observeTransactionsError().observe(viewLifecycleOwner) { throwable ->
                throwable?.let {
                    ErrorDialog(it.message, R.drawable.data_base_error).show(
                        parentFragmentManager,
                        DATABASE_ERROR_TAG
                    )
                }
            }

            getTransactions()
        }
    }

    private fun showProgress() {
        binding.loadingTransactions.visibility = View.VISIBLE
        binding.rvOperations.visibility = View.GONE
        binding.noTransactionsImage.visibility = View.GONE
        binding.noTransactionsText.visibility = View.GONE
    }

    private fun hideProgress() {
        binding.rvOperations.visibility = View.VISIBLE
        binding.loadingTransactions.visibility = View.GONE
        binding.noTransactionsImage.visibility = View.GONE
        binding.noTransactionsText.visibility = View.GONE
    }

    private fun showTransactions() {
        binding.rvOperations.visibility = View.VISIBLE
        binding.noTransactionsImage.visibility = View.GONE
        binding.noTransactionsText.visibility = View.GONE
    }

    private fun hideTransactions() {
        binding.rvOperations.visibility = View.GONE
        binding.noTransactionsImage.visibility = View.VISIBLE
        binding.noTransactionsText.visibility = View.VISIBLE
    }

}

