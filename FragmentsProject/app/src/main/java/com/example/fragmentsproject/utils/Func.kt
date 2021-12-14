package com.example.fragmentsproject.utils

import android.view.ViewGroup
import androidx.fragment.app.FragmentContainerView
import com.example.fragmentsproject.R
import com.example.fragmentsproject.databinding.ActivityMainBinding


fun ActivityMainBinding.createNewFragmentContainer() {
    val myViewGroup = FragmentContainerView(root.context)
    myViewGroup.layoutParams =
        ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    myViewGroup.id = R.id.third_fragment_container_view_tag
    rootLayout.addView(myViewGroup)
}

