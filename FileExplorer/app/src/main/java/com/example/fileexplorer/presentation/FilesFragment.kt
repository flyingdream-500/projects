package com.example.fileexplorer.presentation

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fileexplorer.data.model.ExternalFileItem
import com.example.fileexplorer.databinding.FragmentFilesBinding
import com.example.fileexplorer.presentation.MainActivity.Companion.TAG
import com.example.fileexplorer.presentation.recycler.FilesListAdapter
import com.example.fileexplorer.presentation.viewmodel.SharedViewModel
import com.example.fileexplorer.presentation.viewmodel.ViewModelFactory
import com.example.fileexplorer.utils.Constants.ARG_EXTERNAL_PATH
import com.example.fileexplorer.utils.Constants.ARG_EXTERNAL_URI
import java.io.File


class FilesFragment : Fragment() {

    private var BASE_PATH = Environment.getExternalStorageDirectory().absolutePath

    private val sharedViewModel by activityViewModels<SharedViewModel> {
        ViewModelFactory(application = requireActivity().application)
    }

    private var fileListAdapter = FilesListAdapter()

    //binding
    private var _binding: FragmentFilesBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "fragment onCreate")
        arguments?.let {
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
                val uri = it.getParcelable<Uri>(ARG_EXTERNAL_URI)
                uri?.let {
                    Log.d(TAG, "uri $uri")
                    val docFile = DocumentFile.fromTreeUri(requireContext(), uri)
                    //val docFile = DocumentFile.fromSingleUri(requireContext(), uri)
                    Log.d(TAG, "changed uri ${docFile?.uri}")
                    sharedViewModel.setDocumentFile(docFile)
                }
            } else {
                val path = it.getString(ARG_EXTERNAL_PATH) ?: BASE_PATH
                sharedViewModel.changePath(path)
                sharedViewModel.addElement(path)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("TAGG", "fragment onCreateView")
        _binding = FragmentFilesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("TAGG", "fragment onViewCreated")

        /*sharedViewModel.observeExternalItems().observe(viewLifecycleOwner) { list ->
            if (list.isNotEmpty()) {
                binding.noDataLogo.visibility = View.GONE
                binding.noDataText.visibility = View.GONE
                binding.rvExternalFolders.visibility = View.VISIBLE
                fileListAdapter.updateItems(list)
            } else {
                binding.noDataLogo.visibility = View.VISIBLE
                binding.noDataText.visibility = View.VISIBLE
                binding.rvExternalFolders.visibility = View.GONE
            }
        }
*/
        /*sharedViewModel.observeExternalDocumentFile().observe(viewLifecycleOwner) {
            listFiles()
        }
*/
        binding.rvExternalFolders.apply {
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = fileListAdapter
        }

        listFiles()
    }



    private fun listFiles() {
        Log.d(TAG, "listFiles()")
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            val doc = sharedViewModel.getDocumentFile()
            doc?.let {
                val list = sharedViewModel.getDocumentFiles(it)
                fileListAdapter.updateItems(list)
            }
        } else {
            val path = sharedViewModel.getPath()
            path?.let {
                val list = sharedViewModel.getExternalFiles(it)
                sharedViewModel.setItems(list)
                fileListAdapter.updateItems(list)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val path = File(sharedViewModel.getPath())
        sharedViewModel.changePath(path.parent)
        //sharedViewModel.removeElement(sharedViewModel.getPath()!!)
    }

    companion object {
        @JvmStatic
        fun newInstance(path: String): FilesFragment = FilesFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_EXTERNAL_PATH, path)
            }
        }

        @JvmStatic
        fun newInstanceUri(uri: Uri): FilesFragment = FilesFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_EXTERNAL_URI, uri)
            }
        }
    }
}