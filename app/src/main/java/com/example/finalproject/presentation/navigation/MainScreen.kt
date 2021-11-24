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
import com.example.finalproject.utils.constants.UrlConstants.NETWORK_ERROR_TAG
import com.example.finalproject.utils.extensions.RecyclerViewExtensions.addHorizontalLinearLayout
import com.example.finalproject.utils.extensions.RecyclerViewExtensions.addVerticalDivider
import dagger.hilt.android.AndroidEntryPoint


/**
 * Домашний фрагмента с отображением банковской карты с балансом
 * и 3-х первых курсов валют по отношению к доллару США
 */
@AndroidEntryPoint
class MainScreen : Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding
        get() = _binding!!

    private val currencyAdapter = CurrencyAdapter()

    //Ленивая имплементация sharedViewModel c AndroidViewModelFactory
    //private val sharedViewModel by activityViewModels<SharedViewModel> { viewModelFactory }
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

        //Log.d("MVVM", "${sharedViewModel.hashCode()}")

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

    private fun initUserData() {
        mainViewModel.observeUser().observe(viewLifecycleOwner) { user ->
            binding.userName.text =
                String.format(getString(R.string.user_name), user.name, user.surname)
            binding.userLogo.setImageURI(Uri.parse(user.avatar))
        }

        mainViewModel.getUser()

    }

    private fun initCurrenciesList() {

        binding.rvCurrencies.apply {
            adapter = currencyAdapter
            addVerticalDivider(requireContext())
        }

        mainViewModel.run {

            observeCurrency().observe(viewLifecycleOwner) { currency ->
                currencyAdapter.setCurrency(currency.take(3))
            }

            observeCurrencyProgress().observe(viewLifecycleOwner) { inProgress ->
                binding.run {
                    if (inProgress) {
                        showProgress()
                    } else {
                        hideProgress()
                    }
                }
            }

            observeCurrencyError().observe(viewLifecycleOwner) { throwable ->
                throwable?.let {
                    ErrorDialog(throwable.message, R.drawable.network_error).show(
                        parentFragmentManager,
                        NETWORK_ERROR_TAG
                    )
                }
                //currencyThrowableToNull()

                binding.showRetry()
            }

            getCurrentCurrency()
        }
    }

    private fun initCardsList() {

        val cardAdapter = MainCardAdapter()
        binding.rvCards.addHorizontalLinearLayout(requireContext())
        binding.rvCards.adapter = cardAdapter
        val snapHelper = LinearSnapHelper() // Or PagerSnapHelper
        snapHelper.attachToRecyclerView(binding.rvCards)

        mainViewModel.observeBankCards().observe(viewLifecycleOwner) { bankCards ->
            cardAdapter.setBankCards(bankCards)
        }
        mainViewModel.getBankCards()

    }

    private fun FragmentMainScreenBinding.showRetry() {
        loadingCurrency.visibility = View.GONE
        rvCurrencies.visibility = View.GONE
        retry.visibility = View.VISIBLE
    }

    private fun FragmentMainScreenBinding.showProgress() {
        loadingCurrency.visibility = View.VISIBLE
        rvCurrencies.visibility = View.GONE
        retry.visibility = View.GONE
    }

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