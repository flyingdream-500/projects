package com.example.voicerecorderapp.presentation.fragments

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.voicerecorderapp.databinding.VoiceRecorderLayoutBinding
import com.example.voicerecorderapp.presentation.appComponent
import com.example.voicerecorderapp.presentation.viewmodel.SharedViewModel
import com.example.voicerecorderapp.presentation.viewmodel.ViewModelFactory
import com.example.voicerecorderapp.service.RecordingService
import com.example.voicerecorderapp.utils.permission.RecordingPermissions
import com.example.voicerecorderapp.utils.permission.RecordingPermissions.checkPermission
import com.example.voicerecorderapp.utils.permission.RecordingPermissions.requestPermissions
import javax.inject.Inject


class VoiceRecorderFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    //Ленивая имплементация sharedViewModel c AndroidViewModelFactory
    private val sharedViewModel by activityViewModels<SharedViewModel> { viewModelFactory }


    //Биндинг
    private var _binding: VoiceRecorderLayoutBinding? = null
    private val binding
        get() = _binding!!


    //Сервис
    private var isBound = false
    private var messenger: Messenger? = null
    private var clientMessenger: Messenger = Messenger(RecordingFragmentHandler())
    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            messenger = Messenger(p1)
            isBound = true
            sendMessageToService()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            messenger = null
            isBound = false
        }
    }

    inner class RecordingFragmentHandler: Handler() {
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                UPDATE_TIMER -> {
                    val stroke = msg.data.getString(COUNTER_KEY)
                    stroke?.let {
                        Log.d("TGG", "Stroke $stroke")
                        updateTimer(it)
                    }
                }

                PAUSE_RECORDING -> {
                    showPlayStop()
                }
                STOP_RECORDING -> {
                    showPlay()
                    updateAllRecords()
                }
                START_RECORDING -> {
                    showPauseStop()
                }
                MSG_UNBIND -> {
                    requireContext().unbindService(mConnection)
                }
                else -> super.handleMessage(msg)
            }
        }
    }


    fun updateAllRecords() {
        sharedViewModel.getRecordItems()
    }


    override fun onAttach(context: Context) {
        requireActivity().appComponent.voiceRecorderComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAGG", "1: onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TAGG", "1: onCreateView")
        _binding = VoiceRecorderLayoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun updateTimer(currentTime: String) {
        Log.d("TAGG", "1: updateTimer ")
        _binding?.timer?.text = currentTime
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.start.setOnClickListener {
            if (requireContext().checkPermission()) {
                startRecording()
                showPauseStop()
            } else {
                requestPermissions()
            }
        }
        binding.play.setOnClickListener {
            playRecording()
            showPauseStop()
        }
        binding.pause.setOnClickListener {
            pauseRecording()
            showPlayStop()
        }
        binding.stop.setOnClickListener {
            stopRecording()
            showPlay()
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(requireContext(), RecordingService::class.java)
        requireContext().bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        //createTimer()
    }

    override fun onResume() {
        super.onResume()
        //Toast.makeText(requireContext(), isPlaying().toString(), Toast.LENGTH_SHORT).show()
    }

    private fun sendMessageToService() {
        if (isBound) {
            val message = Message.obtain(null, MSG_BIND)
            message.replyTo = clientMessenger
            messenger?.send(message)
        }
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            requireContext().unbindService(mConnection)
            isBound = false
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        RecordingPermissions.checkRequestPermissionResult(
            requestCode,
            permissions,
            grantResults,
            ::startRecording,
            sharedViewModel::getRecordItems,
            this.requireContext()
        )
    }


    fun showPlay() {
        _binding?.run {
            start.visibility = View.VISIBLE
            play.visibility = View.GONE
            pause.visibility = View.GONE
            stop.visibility = View.GONE
            recordingAnimation.pauseAnimation()
            recordingAnimation.progress = 0f
        }
    }

    fun showPauseStop() {
        _binding?.run {
            start.visibility = View.GONE
            play.visibility = View.GONE
            pause.visibility = View.VISIBLE
            stop.visibility = View.VISIBLE
            recordingAnimation.playAnimation()
        }
    }

    fun showPlayStop() {
        _binding?.run {
            start.visibility = View.GONE
            play.visibility = View.VISIBLE
            pause.visibility = View.GONE
            stop.visibility = View.VISIBLE
            recordingAnimation.pauseAnimation()
            recordingAnimation.progress = 0f
        }
    }

    private fun startRecording() {
        Log.d("TAGG", "1: startRecording")
        val intent = Intent(requireContext(), RecordingService::class.java)
        //requireContext().unbindService( mConnection)
        requireContext().bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        //val intent = Intent(context, RecordingService::class.java)
        intent.action = RecordingService.ACTION_START
        requireContext().startService(intent)
    }

    private fun playRecording() {
        val intent = Intent(context, RecordingService::class.java)
        intent.action = RecordingService.ACTION_PLAY
        requireContext().startService(intent)
    }

    private fun pauseRecording() {
        val intent = Intent(context, RecordingService::class.java)
        intent.action = RecordingService.ACTION_PAUSE
        requireContext().startService(intent)
    }

    private fun stopRecording() {
        val intent = Intent(context, RecordingService::class.java)
        intent.action = RecordingService.ACTION_STOP
        requireContext().startService(intent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }


    companion object {
        const val MSG_BIND = 5
        const val MSG_UNBIND = 6

        const val STOP_RECORDING = 7
        const val START_RECORDING = 8
        const val PAUSE_RECORDING = 9
        const val UPDATE_TIMER = 10

        const val COUNTER_KEY = "COUNTER_KEY"
    }

}