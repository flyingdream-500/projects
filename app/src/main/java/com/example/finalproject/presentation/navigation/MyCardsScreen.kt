package com.example.finalproject.presentation.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentCardScreenBinding
import com.example.finalproject.presentation.dialog.ErrorDialog
import com.example.finalproject.presentation.recyclerview.bankcards.BankCardAdapter
import com.example.finalproject.presentation.viewmodel.MyCardsViewModel
import com.example.finalproject.utils.constants.FragmentConstants.DATABASE_ERROR_TAG
import dagger.hilt.android.AndroidEntryPoint

/**
 * Фрагмент для отображения банковских карт
 */
@AndroidEntryPoint
class MyCardsScreen : Fragment() {

    private var _binding: FragmentCardScreenBinding? = null
    private val binding
        get() = _binding!!

    private val myCardsViewModel: MyCardsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBankCards()

    }

    /**
     * Метод запрашивает данные о банковских картах и подписывается на их изменения
     */
    private fun initBankCards() {
        val cardAdapter = BankCardAdapter()

        //Инициализация View Pager для пролистывания банковских карт
        binding.viewpager.apply {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            this.adapter = cardAdapter
        }

        //наблюдение за данными банковских карт
        myCardsViewModel.observeBankCards().observe(viewLifecycleOwner) { cards ->
            cardAdapter.setBankCards(cards)
        }

        //наблюдение за ошибками
        myCardsViewModel.observeError().observe(viewLifecycleOwner) { throwable ->
            throwable?.let {
                ErrorDialog(throwable.message, R.drawable.data_base_error).show(
                    parentFragmentManager,
                    DATABASE_ERROR_TAG
                )
            }
        }

        //делаем запрос на получение данных о банковских картах
        myCardsViewModel.getBankCards()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}