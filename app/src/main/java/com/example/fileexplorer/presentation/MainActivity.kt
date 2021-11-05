package com.example.fileexplorer.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.storage.StorageManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.documentfile.provider.DocumentFile
import com.example.fileexplorer.BuildConfig
import com.example.fileexplorer.databinding.ActivityMainBinding
import com.example.fileexplorer.utils.hasPermissionToAccessSdCard
import com.example.fileexplorer.data.model.ExternalFileItem
import com.example.fileexplorer.presentation.recycler.FilesListAdapter
import com.example.fileexplorer.presentation.viewmodel.SharedViewModel
import com.example.fileexplorer.presentation.viewmodel.ViewModelFactory
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var pathFile: File = Environment.getExternalStorageDirectory()
    private var rootDocumentFile: DocumentFile? = null


    private val sharedViewModel by viewModels<SharedViewModel> {
        ViewModelFactory(application = application)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedViewModel.observeExternalPath().observe(this) {
            binding.pathView.text = sharedViewModel.getPath()
        }

    }

    override fun onResume() {
        super.onResume()
        if (!checkPermission()) {
            requestPermission()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult")
        if (requestCode == VOLUME_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                data?.data?.let {
                    contentResolver.takePersistableUriPermission(
                        it,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                    rootDocumentFile = DocumentFile.fromTreeUri(this, it)
                    //rootDocumentFile = DocumentFile.fromTreeUri(this, Uri.parse("content://com.android.externalstorage.documents/tree/primary%3A/document/primary%3AAndroid"))
                    sharedViewModel.setDocumentFile(rootDocumentFile)
                }
                //listFiles()
            } else {
                requestPermission()
            }
        } else
            super.onActivityResult(requestCode, resultCode, data)
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d(TAG, "onRequestPermissionsResult")
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //listFiles()
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                    Toast.makeText(this, "PLZ GIVE PERMISSION", Toast.LENGTH_LONG).show()
                }
                requestPermission()
            }
        } else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    /*private fun listFiles() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            rootDocumentFile?.let {
                val paths = it.listFiles().toList()
                val items = paths.map { documentFile ->
                    //val f = File(URI(documentFile.uri.path))
                    //Log.d("TAGG", "path = ${f.absolutePath}")
                    ExternalFileItem(
                        documentFile.name ?: "Def",
                        documentFile.listFiles().size,
                        documentFile.isDirectory,
                        uri = documentFile.uri

                    )
                }
                adapter.updateItems(items)
            }
        } else {
            val paths = pathFile.listFiles()
            val items = paths.map { file ->
                ExternalFileItem(file.name, file.listFiles().size, file.isDirectory, file.path)
            }.toList()
            adapter.updateItems(items)
        }
    }*/

    private fun requestPermission() {
        Log.d(TAG, "requestPermission")
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                val uri = Uri.parse("package:${BuildConfig.APPLICATION_ID}")
                startActivity(
                    Intent(
                        Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                        uri
                    )
                )
            }
            Build.VERSION.SDK_INT == Build.VERSION_CODES.Q -> {
                val storageManager = getSystemService(StorageManager::class.java)
                val intent =
                    storageManager.getStorageVolume(pathFile)?.createOpenDocumentTreeIntent()
                intent?.let { startActivityForResult(it, VOLUME_REQUEST_CODE) }
            }
            else -> {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }


    private fun checkPermission() = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> Environment.isExternalStorageManager()
        Build.VERSION.SDK_INT == Build.VERSION_CODES.Q -> rootDocumentFile != null && hasPermissionToAccessSdCard(
            pathFile
        )
        else -> ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        const val TAG = "TAGG"
        private const val PERMISSION_REQUEST_CODE = 333
        private const val VOLUME_REQUEST_CODE = 334

    }
}