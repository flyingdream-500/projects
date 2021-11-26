package com.example.finalproject.presentation.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentCurrencyDetailScreenBinding
import com.example.finalproject.model.bankcard.BankCard
import com.example.finalproject.model.currency.Currency
import com.example.finalproject.model.transaction.CurrencyTransaction
import com.example.finalproject.presentation.dialog.ErrorDialog
import com.example.finalproject.presentation.dialog.settings.AuthenticationDialog
import com.example.finalproject.presentation.recyclerview.buyingcards.BuyingCardAdapter
import com.example.finalproject.presentation.viewmodel.DetailScreenViewModel
import com.example.finalproject.utils.common.Abbreviation.USD
import com.example.finalproject.utils.constants.DefaultConstants.Format.CURRENCY_DATE_FORMAT
import com.example.finalproject.utils.constants.FragmentConstants.AUTHENTICATION_FRAGMENT_TAG
import com.example.finalproject.utils.constants.FragmentConstants.CURRENCY_ITEM_KEY
import com.example.finalproject.utils.constants.FragmentConstants.DATABASE_ERROR_TAG
import com.example.finalproject.utils.extensions.EditTextExtensions.baseUpdate
import com.example.finalproject.utils.extensions.EditTextExtensions.initDetailInputFilters
import com.example.finalproject.utils.extensions.EditTextExtensions.rightDrawable
import com.example.finalproject.utils.extensions.EditTextExtensions.targetUpdate
import com.example.finalproject.utils.extensions.RecyclerViewExtensions.addHorizontalLinearLayout
import dagger.hilt.android.AndroidEntryPoint
import java.math.RoundingMode
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


    private val detailScreenViewModel: DetailScreenViewModel by viewModels()

    // Адаптер банковских карт
    private val cardAdapter = BuyingCardAdapter()

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

        initCardsList()

        initBuyingTrigger(currency)

        initBaseBankCard(currency)

        initFieldIcons(currency)

        initBuyButton(currency)

    }

    /**
     * Наблюдение за триггером покупки после получения кода верификации из СМС
     */
    private fun initBuyingTrigger(currency: Currency) {
        detailScreenViewModel.observeBuyingTrigger().observe(viewLifecycleOwner) { isTriggered ->
            if (isTriggered) {
                val baseSum = binding.baseRate.text.toString().toFloatOrNull()
                val targetSum = binding.targetRate.text.toString().toFloatOrNull()
                if (baseSum != null && targetSum != null) {
                    changeBalance(baseSum, targetSum, currency)
                }
            }
        }
    }


    /**
     * Метод запрашивает данные о банковских картах пользователя
     */
    private fun initCardsList() {

        binding.rvCards.apply {
            adapter = cardAdapter
            addHorizontalLinearLayout(requireContext())
        }

        //наблюдение за банковскими картами
        detailScreenViewModel.observeBankCards().observe(viewLifecycleOwner) { bankCards ->
            cardAdapter.setBankCards(bankCards)
        }

        detailScreenViewModel.observeError().observe(viewLifecycleOwner) { throwable ->
            throwable?.let {
                ErrorDialog(throwable.message, R.drawable.data_base_error).show(
                    parentFragmentManager,
                    DATABASE_ERROR_TAG
                )
            }
        }

        //делаем запрос на получение банковских карт
        detailScreenViewModel.getBankCards()

    }

    /**
     * Метод инициализации иконок в полях ввода
     */
    private fun initFieldIcons(currency: Currency) {
        binding.run {
            with(baseRate) {
                rightDrawable(R.drawable.ic_us, R.dimen.input_field_icon_size)
            }
            with(targetRate) {
                rightDrawable(currency.logo, R.dimen.input_field_icon_size)
            }
        }
    }

    /**
     * Метод инициализации кнопки покупки валюты
     */
    private fun initBuyButton(currency: Currency) {
        binding.buy.setOnClickListener {
            val baseSum = binding.baseRate.text.toString().toFloatOrNull()
            val targetSum = binding.targetRate.text.toString().toFloatOrNull()
            if (baseSum != null && targetSum != null) {
                beforeBuyingCurrency(baseSum, targetSum, currency)
            }
        }
    }

    /**
     * Метод инициализации базовой банковской карты
     */
    private fun initBaseBankCard(currency: Currency) {

        detailScreenViewModel.observeBaseBankCard().observe(viewLifecycleOwner) { baseBankCard ->
            binding.run {
                with(baseRate) {
                    initDetailInputFilters(1f, baseBankCard.balance.toFloat())
                    addTextChangedListener { text ->
                        targetRate.baseUpdate(
                            text,
                            isFocused,
                            currency.rate,
                            RoundingMode.FLOOR
                        )
                    }
                }
                with(targetRate) {
                    initDetailInputFilters(currency.rate, baseBankCard.balance.toFloat())
                    addTextChangedListener { text ->
                        baseRate.targetUpdate(
                            text,
                            isFocused,
                            currency.rate,
                            RoundingMode.HALF_UP
                        )
                    }
                }
            }
        }

        detailScreenViewModel.getBankCardByAbbr(USD.name)
    }


    /**
     * Функция проверки, нужна ли аутентификация перед покупкой валюты
     */
    private fun beforeBuyingCurrency(baseSum: Float, targetSum: Float, currency: Currency) {
        if (baseSum == 0f || targetSum == 0f)
            return

        detailScreenViewModel.run {
            if (detailScreenViewModel.getSettingsAuth()) {
                AuthenticationDialog().show(
                    parentFragmentManager,
                    AUTHENTICATION_FRAGMENT_TAG
                )
            } else {
                buyingCurrency(baseSum, targetSum, currency)
            }
        }
    }

    /**
     * Функция покупки валюты и обновления полей ввода
     */
    private fun buyingCurrency(baseSum: Float, targetSum: Float, currency: Currency) {
        changeBalance(baseSum, targetSum, currency)
        addTransaction(baseSum, targetSum, currency)
    }


    /**
     * Функция изменения баланса и обновления полей ввода
     */
    private fun changeBalance(baseSum: Float, targetSum: Float, currency: Currency) {
        val bankCards = cardAdapter.getBankCards()
        val baseBankCard = bankCards.getBaseBankCard()
        val targetBankCard = bankCards.getTargetBankCard(currency.abbreviation)

        baseBankCard?.let {

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
                    color = currency.color,
                    description = currency.description
                )

                detailScreenViewModel.addBankCards(baseBankCard, bankCard)
            }
            binding.updateAfterBuying(currency.rate, baseBankCard.balance.toFloat())
        }
    }

    /**
     * Добавление в БД выполненной валютной операции
     */
    private fun addTransaction(baseSum: Float, targetSum: Float, currency: Currency) {
        val transaction = CurrencyTransaction(
            baseSum = String.format(Locale.ENGLISH, getString(R.string.us_character), baseSum),
            targetSum = String.format(Locale.ENGLISH, getString(currency.sign), targetSum),
            baseLogo = R.drawable.ic_us,
            targetLogo = currency.logo,
            date = SimpleDateFormat(CURRENCY_DATE_FORMAT, Locale.getDefault()).format(Date())
        )
        detailScreenViewModel.addTransaction(transaction)
    }


    /**
     * Метод уменьшения баланса карты
     */
    private fun BankCard.downgradeBalance(value: Float) {
        balance -= value
    }

    /**
     * Метод увеличения баланса карты
     */
    private fun BankCard.upgradeBalance(value: Float) {
        balance += value
    }

    /**
     * Получаем целевую банковскую карту
     */
    private fun List<BankCard>.getTargetBankCard(targetAbbreviation: String) =
        this.find { it.abbreviation == targetAbbreviation }?.copy()

    /**
     * Получаем базовую банковскую карту
     */
    private fun List<BankCard>.getBaseBankCard() =
        this.find { it.abbreviation == USD.name }?.copy()


    /**
     * Обновление полей ввода после покупки валюты
     */
    private fun FragmentCurrencyDetailScreenBinding.updateAfterBuying(
        currency: Float,
        balance: Float
    ) {
        with(baseRate) {
            initDetailInputFilters(1f, balance)
            text?.clear()
        }
        with(targetRate) {
            initDetailInputFilters(currency, balance)
            text?.clear()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}