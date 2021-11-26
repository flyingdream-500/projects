package com.example.finalproject.presentation.navigation

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentMainScreenBinding
import com.example.finalproject.presentation.dialog.ErrorDialog
import com.example.finalproject.presentation.recyclerview.currency.CurrencyAdapter
import com.example.finalproject.presentation.recyclerview.maincards.MainCardAdapter
import com.example.finalproject.presentation.viewmodel.MainViewModel
import com.example.finalproject.utils.constants.FragmentConstants.NETWORK_ERROR_TAG
import com.example.finalproject.utils.extensions.RecyclerViewExtensions.addHorizontalLinearLayout
import com.example.finalproject.utils.extensions.RecyclerViewExtensions.addVerticalDivider
import dagger.hilt.android.AndroidEntryPoint


/**
 * Домашний фрагмента с отображением
 * 3-х первых банковских карт,
 * 3-х первых курсов валют по отношению к доллару США
 */
@AndroidEntryPoint
class MainScreen : Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding
        get() = _binding!!


    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCurrenciesList()

        initUserData()

        initCardsList()

        binding.currencySeeAll.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainScreen_to_currencyListScreen)
        }

        binding.retry.setOnClickListener {
            mainViewModel.getCurrentCurrency()
        }

    }

    /**
     * Метод запрашивает данные по пользователю и подписывается на их изменения
     */
    private fun initUserData() {

        //наблюдение за данными пользователя
        mainViewModel.observeUser().observe(viewLifecycleOwner) { user ->
            binding.userName.text =
                String.format(getString(R.string.user_name), user.name, user.surname)
            binding.userLogo.setImageURI(Uri.parse(user.avatar))
        }

        //делаем запрос на получение данных пользователя
        mainViewModel.getUser()
    }


    /**
     * Метод запрашивает данные по курсам валют и подписывается на их изменения
     */
    private fun initCurrenciesList() {

        val currencyAdapter = CurrencyAdapter()

        binding.rvCurrencies.apply  {
            adapter = currencyAdapter
            addVerticalDivider(requireContext())
        }

        mainViewModel.run {

            //наблюдение за курсами валют
            observeCurrency().observe(viewLifecycleOwner) { currency ->
                currencyAdapter.setCurrency(currency.take(3))
            }

            //наблюдение за прогрессом загрузки курсов валют
            observeCurrencyProgress().observe(viewLifecycleOwner) { inProgress ->
                if (inProgress) {
                    binding.showProgress()
                } else {
                    binding.hideProgress()
                }
            }

            //наблюдение за ошибками при загрузке данных и отображени диалогового окна
            observeError().observe(viewLifecycleOwner) { throwable ->
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
     * Метод запрашивает данные о банковских картах пользователя
     */
    private fun initCardsList() {

        val cardAdapter = MainCardAdapter()

        binding.rvCards.apply {
            adapter = cardAdapter
            addHorizontalLinearLayout(requireContext())
        }

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvCards)


        binding.cardsSeeAll.setOnClickListener {
            it.findNavController().navigate(R.id.bankCardsScreen)
        }

        //наблюдение за банковскими картами
        mainViewModel.observeBankCards().observe(viewLifecycleOwner) { bankCards ->
            if(bankCards.size > 3) {
                cardAdapter.setBankCards(bankCards.take(3))
                binding.cardsSeeAll.visibility = View.VISIBLE
            } else {
                cardAdapter.setBankCards(bankCards)
                binding.cardsSeeAll.visibility = View.GONE
            }
        }

        //делаем запрос на получение банковских карт
        mainViewModel.getBankCards()

    }

    //Показать кнопку повторить запрос
    private fun FragmentMainScreenBinding.showRetry() {
        loadingCurrency.visibility = View.GONE
        rvCurrencies.visibility = View.GONE
        retry.visibility = View.VISIBLE
    }

    //Показать  прогресс загрузки курсов валют
    private fun FragmentMainScreenBinding.showProgress() {
        loadingCurrency.visibility = View.VISIBLE
        rvCurrencies.visibility = View.GONE
        retry.visibility = View.GONE
    }

    //Скрыть  прогресс загрузки курсов валют
    private fun FragmentMainScreenBinding.hideProgress() {
        loadingCurrency.visibility = View.GONE
        retry.visibility = View.GONE
        rvCurrencies.visibility = View.VISIBLE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}