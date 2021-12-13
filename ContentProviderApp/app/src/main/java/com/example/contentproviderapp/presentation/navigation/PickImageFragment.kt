package com.example.contentproviderapp.presentation.navigation

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.contentproviderapp.R
import com.example.contentproviderapp.appComponent
import com.example.contentproviderapp.databinding.FragmentGalleryBinding
import com.example.contentproviderapp.presentation.recyclerview.gallery.ImagesAdapter
import com.example.contentproviderapp.presentation.viewmodel.SharedViewModel
import com.example.contentproviderapp.presentation.viewmodel.ViewModelFactory
import com.example.contentproviderapp.utils.permission.ExternalStoragePermission.checkStoragePermission
import com.example.contentproviderapp.utils.permission.ExternalStoragePermission.requestExternalPermission
import com.example.contentproviderapp.utils.permission.ExternalStoragePermission.requestExternalPermissionsResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class PickImageFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    // Lazy implementation of sharedViewModel by AndroidViewModelFactory
    private val sharedViewModel by activityViewModels<SharedViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        requireActivity().appComponent.pickImageFragmentComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ImagesAdapter(::pickImage)
        binding.rvImages.run {
            layoutManager = GridLayoutManager(requireContext(), 3)
            this.adapter = adapter
        }

        sharedViewModel.run {
            localImageLiveData.observe(viewLifecycleOwner) { items ->
                if (items.isNullOrEmpty()) {
                    showNoImagesPicture()
                } else {
                    showLocalImages()
                    adapter.setGalleryImages(items)
                }
            }

            localImageProgressLiveData.observe(viewLifecycleOwner) { inProgress ->
                if (inProgress) showProgress() else hideProgress()
            }

            localImageErrorLiveData.observe(viewLifecycleOwner) { throwable ->
                throwable?.let {
                    showError(it.message)
                }
            }

        }

        if (requireContext().checkStoragePermission()) {
            sharedViewModel.getLocalImagesAsyncRx()
        } else {
            requestExternalPermission()
        }

    }

    /**
     * Show No Images picture
     */
    private fun showNoImagesPicture() {
        binding.rvImages.visibility = View.GONE
        binding.noImage.visibility = View.VISIBLE
    }

    /**
     * Show lis of images from Media Content Provider
     */
    private fun showLocalImages() {
        binding.rvImages.visibility = View.VISIBLE
        binding.noImage.visibility = View.GONE
    }

    /**
     * Show error from Media Content Provider
     */
    private fun showError(message: String?) {
        Toast.makeText(
            requireContext(),
            String.format(getString(R.string.load_image_error), message),
            Toast.LENGTH_SHORT
        ).show()
        dialog?.cancel()
    }

    /**
     * Show progress of loading Media Content Provider
     */
    private fun showProgress() {
        binding.rvImages.visibility = View.GONE
        binding.loading.visibility = View.VISIBLE
    }

    /**
     * Hide progress of loading Media Content Provider
     */
    private fun hideProgress() {
        binding.rvImages.visibility = View.VISIBLE
        binding.loading.visibility = View.GONE
    }

    /**
     * Pick image from Media Content Provider
     */
    private fun pickImage(uri: Uri) {
        sharedViewModel.pickImage(uri)
        dialog?.cancel()
    }


    /**
     * Request result of external permission
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        requestExternalPermissionsResult(
            requestCode, permissions, grantResults, sharedViewModel::getLocalImagesAsyncRx
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}