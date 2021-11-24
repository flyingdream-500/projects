package com.example.finalproject.presentation.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentCurrencyDetailScreenBinding
import com.example.finalproject.model.bankcard.BankCard
import com.example.finalproject.model.currency.Currency
import com.example.finalproject.model.transaction.CurrencyTransaction
import com.example.finalproject.presentation.dialog.settings.AuthenticationDialog
import com.example.finalproject.presentation.recyclerview.bankcards.BankCardAdapter
import com.example.finalproject.presentation.viewmodel.DetailScreenViewModel
import com.example.finalproject.utils.common.Abbreviation.USD
import com.example.finalproject.utils.constants.DefaultConstants.Format.CURRENCY_DATE_FORMAT
import com.example.finalproject.utils.constants.DefaultConstants.FragmentBundles.CURRENCY_ITEM_KEY
import com.example.finalproject.utils.constants.SettingsConstants.AUTHENTICATION_FRAGMENT_TAG
import com.example.finalproject.utils.extensions.EditTextExtensions.baseUpdate
import com.example.finalproject.utils.extensions.EditTextExtensions.initDetailInputFilters
import com.example.finalproject.utils.extensions.EditTextExtensions.rightDrawable
import com.example.finalproject.utils.extensions.EditTextExtensions.targetUpdate
import com.example.finalproject.utils.extensions.RecyclerViewExtensions.addHorizontalLinearLayout
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


/**
 * Фрагмент покупки валюты за доллары США
 */
@AndroidEntryPoint
class CurrencyDetailScreen : Fragment() {

    private var _binding: FragmentCurrencyDetailScreenBinding? = null
    private val binding
        get() = _binding!!

//    @Inject
//    lateinit var viewModelFactory: ViewModelFactory
//    private val sharedViewModel by activityViewModels<SharedViewModel> { viewModelFactory }
    private val detailScreenViewModel: DetailScreenViewModel by viewModels()


    private val cardAdapter = BankCardAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyDetailScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currency =
            requireArguments().getParcelable<Currency>(CURRENCY_ITEM_KEY) as Currency

        detailScreenViewModel.observeTransactionsAdding().observe(viewLifecycleOwner) { isAdded ->
            if (!isAdded) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.transaction_not_added),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }



        initInputFields(currency)

        initCardsList()

    }

    private fun initCardsList() {

        binding.rvCards.addHorizontalLinearLayout(requireContext())
        binding.rvCards.adapter = cardAdapter
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvCards)


        detailScreenViewModel.observeBankCards().observe(viewLifecycleOwner) { bankCards ->
            cardAdapter.setBankCards(bankCards)
        }

        detailScreenViewModel.getBankCards()

    }

    private fun initInputFields(currency: Currency) {

        binding.run {


            with(baseRate) {
                rightDrawable(R.drawable.ic_us, R.dimen.input_field_icon_size)
            }
            with(targetRate) {
                rightDrawable(currency.logo, R.dimen.input_field_icon_size)
            }

            with(buy) {
                setOnClickListener {
                    val baseSum = baseRate.text.toString().toFloatOrNull()
                    val targetSum = targetRate.text.toString().toFloatOrNull()
                    if (baseSum != null || targetSum != null) {
                        beforeBuyingCurrency(baseSum!!, targetSum!!, currency)
                    }
                }
            }
        }

        detailScreenViewModel.observeBankCard().observe(viewLifecycleOwner) { baseBankCard ->

            binding.run {
                with(baseRate) {
                    initDetailInputFilters(1f, baseBankCard.balance.toFloat())
                    addTextChangedListener { text ->
                        targetRate.baseUpdate(text, isFocused, currency.rate, java.math.RoundingMode.FLOOR)
                    }
                }

                with(targetRate) {
                    initDetailInputFilters(currency.rate, baseBankCard.balance.toFloat())
                    addTextChangedListener { text ->
                        baseRate.targetUpdate(text, isFocused, currency.rate, java.math.RoundingMode.HALF_UP)
                    }
                }
            }
        }

        detailScreenViewModel.getBankCardByAbbr(USD.name)

    }


    //Функция проверки, нужна ли аутентификация перед покупкой валюты
    private fun beforeBuyingCurrency(baseSum: Float, targetSum: Float, currency: Currency) {
        detailScreenViewModel.run {
            if (detailScreenViewModel.getSettingsAuth()) {
                AuthenticationDialog { buyingCurrency(baseSum, targetSum, currency) }.show(
                    parentFragmentManager,
                    AUTHENTICATION_FRAGMENT_TAG
                )
            } else {
                buyingCurrency(baseSum, targetSum, currency)
            }
        }
    }

    //Функция покупки валюты и обновления полей ввода
    private fun buyingCurrency(baseSum: Float, targetSum: Float, currency: Currency) {
        changeBalance(baseSum, targetSum, currency)
        addTransaction( baseSum, targetSum, currency)
    }



    private fun changeBalance(baseSum: Float, targetSum: Float, currency: Currency) {
        val bankCards = cardAdapter.getBankCards()
        val baseBankCard = bankCards.getBaseBankCard()
        val targetBankCard = bankCards.getTargetBankCard(currency.abbreviation)

        baseBankCard.downgradeBalance(baseSum)
        if (targetBankCard != null) {
            targetBankCard.upgradeBalance(targetSum)
            detailScreenViewModel.addBankCards(baseBankCard, targetBankCard)
        } else {
            val bankCard = BankCard(
                balance = targetSum.toDouble(),
                logo = currency.logo,
                currency = currency.sign,
                abbreviation = currency.abbreviation,
                color = currency.color
            )
            detailScreenViewModel.addBankCards(baseBankCard, bankCard)
        }
        binding.updateAfterBuying(currency.rate, baseBankCard.balance.toFloat())
    }



    private fun BankCard.downgradeBalance(value: Float) {
        balance -= value
    }

    private fun BankCard.upgradeBalance(value: Float) {
        balance += value
    }

    private fun List<BankCard>.getTargetBankCard(targetAbbreviation: String) =
        this.find { it.abbreviation == targetAbbreviation }?.copy()

    private fun List<BankCard>.getBaseBankCard() =
        this[0].copy()


    //Обновление полей ввода после покупки валюты
    private fun FragmentCurrencyDetailScreenBinding.updateAfterBuying(currency: Float, balance: Float) {
        with(baseRate) {
            initDetailInputFilters(1f, balance)
            text!!.clear()
        }
        with(targetRate) {
            initDetailInputFilters(currency, balance)
            text!!.clear()
        }
    }



    // Создание класса - модели валютной операции
    private fun addTransaction( baseSum: Float, targetSum: Float, currency: Currency) {
        val transaction = CurrencyTransaction(
            baseSum = String.format(Locale.ENGLISH, getString(R.string.us_character), baseSum),
            targetSum = String.format(Locale.ENGLISH, getString(currency.sign), targetSum),
            baseLogo = R.drawable.ic_us,
            targetLogo = currency.logo,
            date = SimpleDateFormat(CURRENCY_DATE_FORMAT, Locale.getDefault()).format(Date())
        )
        detailScreenViewModel.addTransaction(transaction)
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}