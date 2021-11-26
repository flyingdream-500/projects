package com.example.finalproject.presentation.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentCurrencyListScreenBinding
import com.example.finalproject.presentation.dialog.ErrorDialog
import com.example.finalproject.presentation.recyclerview.currency.CurrencyAdapter
import com.example.finalproject.presentation.viewmodel.ListScreenViewModel
import com.example.finalproject.utils.constants.FragmentConstants.NETWORK_ERROR_TAG
import com.example.finalproject.utils.extensions.RecyclerViewExtensions.addVerticalDivider
import dagger.hilt.android.AndroidEntryPoint

/**
 * Фрагмент с отображением курсов валют
 */
@AndroidEntryPoint
class CurrencyListScreen : Fragment() {

    private var _binding: FragmentCurrencyListScreenBinding? = null
    private val binding
        get() = _binding!!

    private val listScreenViewModel: ListScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyListScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCurrenciesList()

        initRetry()
    }

    /**
     * Метод инициализации кнопки повтора запроса
     */
    private fun initRetry() {
        binding.retry.setOnClickListener {
            listScreenViewModel.getCurrentCurrency()
        }
    }


    /**
     * Метод запрашивает данные по курсам валют и подписывается на их изменения
     */
    private fun initCurrenciesList() {

        val currencyAdapter = CurrencyAdapter()

        binding.rvCurrencies.apply {
            adapter = currencyAdapter
            addVerticalDivider(requireContext())
        }

        listScreenViewModel.run {

            //наблюдение за курсами валют
            observeCurrency().observe(viewLifecycleOwner) { currency ->
                currencyAdapter.setCurrency(currency)
            }

            //наблюдение за прогрессом загрузки курсов валют
            observeCurrencyProgress().observe(viewLifecycleOwner) { inProgress ->
                binding.run {
                    if (inProgress) {
                        showProgress()
                    } else {
                        hideProgress()
                    }
                }
            }

            //наблюдение за ошибками при загрузке данных и отображени диалогового окна
            observeCurrencyError().observe(viewLifecycleOwner) { throwable ->
                throwable?.let {
                    ErrorDialog(throwable.message, R.drawable.network_error).show(
                        parentFragmentManager,
                        NETWORK_ERROR_TAG
                    )
                }
                binding.showRetry()
            }

            //делаем запрос на получение текущих курсов валют
            getCurrentCurrency()
        }
    }

    /**
     * Показываем кнопку retry, скрывавем остальной UI
     */
    private fun FragmentCurrencyListScreenBinding.showRetry() {
        loadingCurrency.visibility = View.GONE
        rvCurrencies.visibility = View.GONE
        retry.visibility = View.VISIBLE
    }

    /**
     * Показываем прогресс, скрывавем остальной UI
     */
    private fun FragmentCurrencyListScreenBinding.showProgress() {
        loadingCurrency.visibility = View.VISIBLE
        rvCurrencies.visibility = View.GONE
        retry.visibility = View.GONE
    }

    /**
     * Скрываем прогресс, показываем остальной UI
     */
    private fun FragmentCurrencyListScreenBinding.hideProgress() {
        loadingCurrency.visibility = View.GONE
        retry.visibility = View.GONE
        rvCurrencies.visibility = View.VISIBLE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}