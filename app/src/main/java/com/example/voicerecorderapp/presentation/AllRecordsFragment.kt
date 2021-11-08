package com.example.voicerecorderapp.presentation

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RemoteViews
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.voicerecorderapp.R
import com.example.voicerecorderapp.data.RecordItem
import com.example.voicerecorderapp.databinding.AllRecordsLayoutBinding
import com.example.voicerecorderapp.presentation.recyclerview.RecordsAdapter
import java.io.File


class AllRecordsFragment: Fragment() {

    private var _binding: AllRecordsLayoutBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TAGG", "2: onCreate")
        _binding = AllRecordsLayoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("TAGG", "2: onViewCreated")
        if (checkPermission()) {
            showRecords()
        } else {
            binding.permissionsDenied.visibility = View.VISIBLE
            binding.permissionsDenied.setOnClickListener {
                requestPermissions()
            }
            requestPermissions()
        }


    }

    override fun onResume() {
        super.onResume()
        Log.d("TAGG", "2: onResume")
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
                    it.name,
                    it.path,
                    it.sizeInKb()
                )
            }
            val adapter = RecordsAdapter()

            binding.rvRecords.apply {
                this.adapter = adapter
                addVerticalDivider(requireContext())
                adapter.setRecordItems(recordItems)
            }
        }

        //createNotification()


        /*val pathFile = Environment.getExternalStorageDirectory()
        val fileName = "${SimpleDateFormat.getTimeInstance().format(Date())}.txt"
        try {
            File(pathFile, fileName).createNewFile()
        } catch (exception: IOException) {
            //Log.e(TAG, "addFile: createNewFile Error", exception)
            //Toast.makeText(this, "addFile: createNewFile Error", Toast.LENGTH_LONG).show()
        }*/
    }


    val CHANNEL_1_ID = "channel listening"

    fun createNotification() {
        val remoteViews = RemoteViews(requireContext().packageName, R.layout.listening_notification_layout)

        val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_1_ID, "listening channel", NotificationManager.IMPORTANCE_HIGH)
            channel.description = "My channel description"
            notificationManager.createNotificationChannel(channel)
        }

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(requireContext(), CHANNEL_1_ID)
            .setSmallIcon(R.drawable.ic_play)
            .setCustomContentView(remoteViews)

        val notification = builder.build()
        notification.flags = notification.flags or Notification.FLAG_ONGOING_EVENT

        notificationManager.notify(120, notification)
    }



    // Добавление разделителя списка курса валют
    fun RecyclerView.addVerticalDivider(context: Context) {
        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        addItemDecoration(divider)
    }

    fun  File.sizeInKb() = length() / 1024

    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        requestPermissions( permissions, EXTERNAL_STORAGE_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            EXTERNAL_STORAGE_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showRecords()
                }
            }
            else -> {

            }
        }
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val EXTERNAL_STORAGE_REQUEST_CODE = 333
    }
}