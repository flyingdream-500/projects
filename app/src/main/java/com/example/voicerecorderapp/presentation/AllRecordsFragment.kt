package com.example.voicerecorderapp.presentation

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.voicerecorderapp.data.RecordItem
import com.example.voicerecorderapp.databinding.AllRecordsLayoutBinding
import com.example.voicerecorderapp.presentation.recyclerview.RecordsAdapter
import com.example.voicerecorderapp.service.ListeningService
import com.example.voicerecorderapp.utils.allrecords.Extensions.addVerticalDivider
import com.example.voicerecorderapp.utils.allrecords.Extensions.sizeInKb
import com.example.voicerecorderapp.utils.allrecords.Permissions
import com.example.voicerecorderapp.utils.allrecords.Permissions.checkPermission
import com.example.voicerecorderapp.utils.allrecords.Permissions.requestPermissions
import java.io.File


class AllRecordsFragment : Fragment() {

    private var _binding: AllRecordsLayoutBinding? = null
    private val binding
        get() = _binding!!

    private val recordsAdapter = RecordsAdapter(::playingTrack)

    private var bound = false
    private var mService: ListeningService? = null
    private var messenger: Messenger? = null
    private var clientMessenger: Messenger = Messenger(IncomingListeningFragmentHandler())


    companion object {
        val MSG_RESPONSE = 2
        val MSG_UNBIND = 3
    }

    inner class IncomingListeningFragmentHandler: Handler() {
        override fun handleMessage(msg: Message) {
            Log.d("SRC", "fragment response")
            when(msg.what) {
                MSG_RESPONSE -> {
                    //requireContext().unbindService(mConnection)
                    resetRecordItems()
                }
                MSG_UNBIND -> {
                    requireContext().unbindService(mConnection)
                }
                else -> super.handleMessage(msg)
            }
        }
    }

    fun resetRecordItems() {
        val items = recordsAdapter.getRecordItems()
        items.forEach { it.isPlaying = false }
        (binding.rvRecords.adapter as RecordsAdapter).setRecordItems(items)
    }

    private fun playingTrack(recordItem: RecordItem, context: Context) {
        Log.d("SRC", "playingTrack")
        if (bound) {
            /*val message = Message.obtain(null, ListeningService.MSG_RECORD)
            val bundle = Bundle()
            bundle.putParcelable("RECORD ITEM KEY", recordItem)
            message.data = bundle
            message.replyTo = clientMessenger
            messenger?.let {
                it.send(message)
            }*/
            resetRecordItems()
            val intent = Intent(context, ListeningService::class.java)
            intent.action = ListeningService.ACTION_START
            intent.putExtra("RECORD ITEM KEY", recordItem)
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {*/
                context.startService(intent)
            //}
        }
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

        permissions()


    }

    override fun onResume() {
        super.onResume()
        sendMessageToService()
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(requireContext(), ListeningService::class.java)
        //requireContext().startService(intent)
        requireContext().bindService(intent, mConnection, Context.BIND_AUTO_CREATE)

    }

    fun sendMessageToService() {
        if (bound) {
            val message = Message.obtain(null, ListeningService.MSG_EXAMPLE, 0, 0)
            message.replyTo = clientMessenger
            messenger?.send(message)
        }
    }

    override fun onStop() {
        super.onStop()
        if (bound) {
            requireContext().unbindService(mConnection)
            bound = false
        }
    }

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            messenger = Messenger(p1)
            bound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            messenger = null
            bound = false
        }
    }





    private fun showRecords() {
        //Log.d("TAGG", "showRecords")

        binding.permissionsDenied.visibility = View.GONE

        val rootFile = File(Environment.getExternalStorageDirectory(), "VoiceRecords/")
        if (!rootFile.exists()) {
            rootFile.mkdirs()
        }
        val files = rootFile.listFiles().toList()
        files?.let {
            val recordItems = it.map {
                RecordItem(
                    it.name.recordName(),
                    it.path,
                    it.sizeInKb()
                )
            }


            binding.rvRecords.apply {
                this.adapter = recordsAdapter
                addVerticalDivider(requireContext())
                recordsAdapter.setRecordItems(recordItems)
            }
        }

        /*val pathFile = Environment.getExternalStorageDirectory()
        val fileName = "${SimpleDateFormat.getTimeInstance().format(Date())}.txt"
        try {
            File(pathFile, fileName).createNewFile()
        } catch (exception: IOException) {
            //Log.e(TAG, "addFile: createNewFile Error", exception)
            //Toast.makeText(this, "addFile: createNewFile Error", Toast.LENGTH_LONG).show()
        }*/
    }

    fun String.recordName() = substringBeforeLast(".")

    private fun permissions() {
        if (requireContext().checkPermission()) {
            showRecords()
        } else {
            requestPermissions()
            binding.permissionsDenied.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    requestPermissions()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Permissions.checkRequestPermissionResult(
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
}