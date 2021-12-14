package com.example.fileexplorer.presentation.recycler

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fileexplorer.R
import com.example.fileexplorer.data.model.ExternalFileItem
import com.example.fileexplorer.databinding.FileItemBinding
import com.example.fileexplorer.presentation.FilesFragment
import com.example.fileexplorer.presentation.MainActivity
import com.example.fileexplorer.utils.Constants.FILES_FRAGMENT_TAG
import com.example.fileexplorer.utils.FileConfig.getType

class FileViewHolder(private val binding: FileItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(itemExternal: ExternalFileItem) {
        binding.run {
            fileName.text = itemExternal.name
            with(itemExternal) {
                if (isDirectory) {
                    logo.setImageDrawable(ContextCompat.getDrawable(root.context, R.drawable.ic_folder))
                    fileCount.text = String.format(root.resources.getString(R.string.file_count), filesCount)
                    itemView.setOnClickListener {
                        val filesListFragment: FilesFragment
                        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
                            filesListFragment = FilesFragment.newInstanceUri(uri!!)
                        } else {
                            filesListFragment = FilesFragment.newInstance(path!!)
                        }

                        (itemView.context as MainActivity).supportFragmentManager
                            .beginTransaction()
                            .add(R.id.fragment_container, filesListFragment)
                            .addToBackStack(FILES_FRAGMENT_TAG)
                            .commit()
                    }
                } else {
                    logo.setImageDrawable(ContextCompat.getDrawable(root.context, path!!.getType()))
                }
            }
        }
    }


    companion object {
        fun create(parent: ViewGroup): FileViewHolder {
            val binding =
                FileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return FileViewHolder(binding)
        }
    }

}