package com.example.voicerecorderapp.presentation.fragments

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.voicerecorderapp.data.model.RecordItem
import com.example.voicerecorderapp.databinding.AllRecordsLayoutBinding
import com.example.voicerecorderapp.presentation.appComponent
import com.example.voicerecorderapp.presentation.recyclerview.RecordsAdapter
import com.example.voicerecorderapp.presentation.viewmodel.SharedViewModel
import com.example.voicerecorderapp.presentation.viewmodel.ViewModelFactory
import com.example.voicerecorderapp.service.ListeningService
import com.example.voicerecorderapp.utils.Extensions.addVerticalDivider
import com.example.voicerecorderapp.utils.permission.ListeningPermissions
import com.example.voicerecorderapp.utils.permission.ListeningPermissions.checkPermission
import com.example.voicerecorderapp.utils.permission.ListeningPermissions.requestPermissions
import javax.inject.Inject


class AllRecordsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    //Ленивая имплементация sharedViewModel c AndroidViewModelFactory
    private val sharedViewModel by activityViewModels<SharedViewModel> { viewModelFactory }


    //Биндинг
    private var _binding: AllRecordsLayoutBinding? = null
    private val binding
        get() = _binding!!

    //Адаптер
    private val recordsAdapter = RecordsAdapter(::playTrack)

    //Сервис
    private var isBound = false
    private var messenger: Messenger? = null
    private var clientMessenger: Messenger = Messenger(ListeningFragmentHandler())
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
    inner class ListeningFragmentHandler: Handler() {
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                STOP_LISTENING -> {
                    val path = msg.data.getString(RECORD_PATH_KEY)
                    Log.d("SRC", "Fragment: STOP_LISTENING: ${path == null}")
                    path?.let {
                        pauseListeningAnimation(it)
                    }
                }
                START_LISTENING -> {
                    val path = msg.data.getString(RECORD_PATH_KEY)
                    Log.d("SRC", "Fragment: START_LISTENING: ${path == null}")
                    path?.let {
                        playListeningAnimation(it)
                    }
                }
                MSG_UNBIND -> {
                    requireContext().unbindService(mConnection)
                }
                else -> super.handleMessage(msg)
            }
        }
    }



    override fun onAttach(context: Context) {
        requireActivity().appComponent.allRecordsComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("SRC", "Fragment: onCreateView")
        _binding = AllRecordsLayoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (requireContext().checkPermission()) {
            observeRecordItems()
            showRecords()
        } else {
            requestPermissions()
            showPermissionDenied()
        }

    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(requireContext(), ListeningService::class.java)
        requireContext().bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        //sendMessageToService()
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            requireContext().unbindService(mConnection)
            isBound = false
        }
    }

    override fun onResume() {
        super.onResume()
        //sendMessageToService()
    }


    private fun observeRecordItems() {
        sharedViewModel.recordsLiveData.observe(viewLifecycleOwner) { recordItems ->
            recordsAdapter.setRecordItems(recordItems)
        }
    }



    private fun clearListeningAnimation() {
        val recordItems = sharedViewModel.recordsLiveData.value
        recordItems?.let { items ->
            items.forEach { it.isPlaying = false }
            sharedViewModel.setRecordItems(items)
        }
    }

    private fun pauseListeningAnimation(path: String) {
        Log.d("SRC", "PAUSE")
        val recordItems = sharedViewModel.recordsLiveData.value
        recordItems?.let { items ->
            items.find { it.path == path }?.isPlaying = false
            sharedViewModel.setRecordItems(items)
        }

        //(binding.rvRecords.adapter as RecordsAdapter).changePlayingStatus(path, false)
    }

    private fun playListeningAnimation(path: String) {
        Log.d("SRC", "START")
        val recordItems = sharedViewModel.recordsLiveData.value
        recordItems?.let { items ->
            items.find { it.path == path }?.isPlaying = true
            sharedViewModel.setRecordItems(items)
        }
        //(binding.rvRecords.adapter as RecordsAdapter).changePlayingStatus(path, true)
    }


    private fun playTrack(recordItem: RecordItem, context: Context) {
        if (isBound) {
            clearListeningAnimation()
            val intent = Intent(context, ListeningService::class.java)
            intent.action = ListeningService.ACTION_START
            intent.putExtra(RECORD_ITEM_KEY, recordItem)
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {*/
                context.startService(intent)
            //}
        }
    }

    private fun sendMessageToService() {
        if (isBound) {
            val message = Message.obtain(null, MSG_BIND)
            message.replyTo = clientMessenger
            messenger?.send(message)
        }
    }


    private fun showPermissionDenied() {
        binding.permissionsDenied.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                requestPermissions()
            }
        }
    }


    private fun showRecords() {
        binding.run {
            permissionsDenied.visibility = View.GONE
            rvRecords.apply {
                this.adapter = recordsAdapter
                addVerticalDivider(requireContext())
            }
        }
        sharedViewModel.getRecordItems()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        ListeningPermissions.checkRequestPermissionResult(
            requestCode,
            permissions,
            grantResults,
            ::showRecords
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("SRC", "Fragment: onDestroyView")
    }


    companion object {
        const val MSG_BIND = 1
        const val MSG_UNBIND = 2
        const val STOP_LISTENING = 3
        const val START_LISTENING = 4

        const val RECORD_ITEM_KEY = "RECORD ITEM KEY"
        const val RECORD_PATH_KEY = "RECORD PATH KEY"
    }
}