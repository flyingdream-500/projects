package com.example.voicerecorderapp.presentation.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.voicerecorderapp.presentation.fragments.AllRecordsFragment
import com.example.voicerecorderapp.presentation.fragments.VoiceRecorderFragment


/**
 *  ViewPager с 2-мя табами для записи звука и просмотра записанных файлов
 */
class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            RECORDING_ID -> VoiceRecorderFragment()
            LISTENING_ID -> AllRecordsFragment()
            else -> VoiceRecorderFragment()
        }
    }

    companion object {
        const val RECORDING_ID = 0
        const val LISTENING_ID = 1
    }

}