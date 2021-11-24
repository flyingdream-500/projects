package com.example.finalproject.presentation.dialog.settings

import android.animation.Animator
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Telephony
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalproject.R
import com.example.finalproject.databinding.BottomSheetAuthenticationBinding
import com.example.finalproject.presentation.receiver.SmsListener
import com.example.finalproject.presentation.receiver.SmsReceiver
import com.example.finalproject.utils.common.interfaces.DefaultAnimatorListener
import com.example.finalproject.utils.constants.SmsReceiverConstants.CALLER_NUMBER
import com.example.finalproject.utils.permission.ReceiveSmsPermission.checkSmsPermission
import com.example.finalproject.utils.permission.ReceiveSmsPermission.requestPermissionsResult
import com.example.finalproject.utils.permission.ReceiveSmsPermission.requestSmsPermission
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.lang.ref.WeakReference


/**
 * Диалоговое окно обработки валютной операции
 */


class AuthenticationDialog(
    private val buy: () -> Unit
) : BottomSheetDialogFragment(), SmsListener {

    private val smsReceiver = SmsReceiver(WeakReference(this))

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

    private fun registerReceiver() {
        requireContext().registerReceiver(
            smsReceiver,
            IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        )
    }

    private fun unregisterReceiver() {
        requireContext().unregisterReceiver(smsReceiver)
    }


    override fun updateVerificationCode(code: String) {
        if (code.isNotEmpty()) {
            buy.invoke()
            receiveEvent(code)
        }
    }

    private fun receiveEvent(code: String) {
        binding.run {
            verifyCode.text = code
            loadingCheck.apply {
                setAnimation(R.raw.loading_to_check)
                repeatCount = 0
                playAnimation()
                addAnimatorListener(object : DefaultAnimatorListener {
                    override fun onAnimationEnd(p0: Animator?) {
                        dialog?.cancel()
                    }
                })
            }
        }
    }

    /**
     * Получение результата запроса на разрешения
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