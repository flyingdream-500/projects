package com.example.finalproject.presentation.dialog.settings

import android.animation.Animator
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Telephony
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.finalproject.R
import com.example.finalproject.databinding.BottomSheetAuthenticationBinding
import com.example.finalproject.presentation.navigation.CurrencyDetailScreen
import com.example.finalproject.presentation.receiver.SmsListener
import com.example.finalproject.presentation.receiver.SmsReceiver
import com.example.finalproject.presentation.viewmodel.DetailScreenViewModel
import com.example.finalproject.utils.common.interfaces.DefaultAnimatorListener
import com.example.finalproject.utils.constants.DefaultConstants.CALLER_NUMBER
import com.example.finalproject.utils.permission.ReceiveSmsPermission.checkSmsPermission
import com.example.finalproject.utils.permission.ReceiveSmsPermission.requestPermissionsResult
import com.example.finalproject.utils.permission.ReceiveSmsPermission.requestSmsPermission
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.lang.ref.WeakReference

/*Шаблон для отправки кода верификации:
*   Ваш код подтверждения валютной операции: 895869.
*   Никому не сообщайте код.
*/



/**
 * Диалоговое окно обработки валютной операции посредством получения СМС кода подтверждения от номера [CALLER_NUMBER]
 */
class AuthenticationDialog : BottomSheetDialogFragment(), SmsListener {

    /**
     * Приемник для отлавливания СМС сообщений
     */
    private val smsReceiver = SmsReceiver(WeakReference(this))

    /**
     * Родительская View Model из фрагмента [CurrencyDetailScreen]
     */
    private val detailScreenViewModel: DetailScreenViewModel by viewModels(
        ownerProducer = {requireParentFragment().childFragmentManager.primaryNavigationFragment!!}
    )

    private var _binding: BottomSheetAuthenticationBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetAuthenticationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Проверка разрешений на получение СМС
        if (!checkSmsPermission())
            requestSmsPermission()

        binding.phoneNumber.text =
            String.format(resources.getString(R.string.phone_number), CALLER_NUMBER)

    }

    override fun onResume() {
        super.onResume()
        registerReceiver()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver()
    }


    /**
     * Регистрируем слушателя СМС сообщений
     */
    private fun registerReceiver() {
        requireContext().registerReceiver(
            smsReceiver,
            IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        )
    }

    /**
     * Отписываем слушателя СМС сообщений
     */
    private fun unregisterReceiver() {
        requireContext().unregisterReceiver(smsReceiver)
    }


    /**
     * Метод для выполнения транзакции и обновления UI после получения кода верификации
     */
    override fun updateVerificationCode(code: String) {
        if (code.isNotEmpty()) {
            detailScreenViewModel.triggerToBuy()
            receiveEvent(code)
        }
    }


    /**
     * Метод для обновления UI после получения кода верификации
     */
    private fun receiveEvent(code: String) {
        binding.run {
            verifyCode.text = code
            loadingCheck.apply {
                setAnimation(R.raw.loading_to_check)
                repeatCount = 0
                playAnimation()
                addAnimatorListener(object : DefaultAnimatorListener {
                    override fun onAnimationEnd(p0: Animator?) {
                        detailScreenViewModel.triggerToClose()
                        dialog?.cancel()
                    }
                })
            }
        }
    }

    /**
     * Получение результата запроса на разрешения СМС
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        requestPermissionsResult(
            requestCode, permissions, grantResults
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}