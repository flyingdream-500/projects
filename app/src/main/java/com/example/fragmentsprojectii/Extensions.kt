package com.example.fragmentsprojectii

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.fragmentsprojectii.databinding.ActivityMainBinding

object Extensions {

    fun FragmentTransaction.backStack(stackSelected: Boolean): FragmentTransaction {
        if (stackSelected) addToBackStack(null)
        return this
    }

    fun FragmentTransaction.addOrReplace(
        addSelected: Boolean,
        id: Int,
        fragment: MainFragment
    ): FragmentTransaction {
        if (addSelected)
            add(id, fragment)
        else
            replace(id, fragment)
        return this
    }


    fun ActivityMainBinding.isAddSelected() = radioAdd.isChecked
    fun ActivityMainBinding.isBackStackSelected() = checkBackstack.isChecked


    /**
     * Добавление фрагмента
     */
    fun FragmentManager.addFr(stackSelected: Boolean, addSelected: Boolean) {

        val stackCount = if (stackSelected) backStackEntryCount.inc() else backStackEntryCount

        val mainFragment = MainFragment.newInstance(stackCount.toString())

        beginTransaction()
            .setCustomAnimations(
                R.animator.slide_to_front,
                R.animator.slide_to_out,
                R.animator.slide_to_front,
                R.animator.slide_to_out
            )
            .addOrReplace(
                addSelected,
                R.id.fragment_container_view,
                mainFragment
            )
            .backStack(stackSelected)
            .commit()

        executePendingTransactions()
    }


    /**
     * Удаление фрагмента
     */
    fun FragmentManager.deleteFr(stackSelected: Boolean) {
        if (stackSelected) {
            popBackStack()
        } else {
            if (fragments.size > 0) {
                beginTransaction()
                    .setCustomAnimations(
                        R.animator.slide_to_front,
                        R.animator.slide_to_out,
                        R.animator.slide_to_front,
                        R.animator.slide_to_out
                    )
                    .remove(fragments.get(fragments.size - 1))
                    .commit()
            }
        }
        executePendingTransactions()
        kostyl()
        executePendingTransactions()
    }

    /**
     * @see R.raw.kostyl
     * Очистка стэка после удаления последнего фрагмента
     */
    private fun FragmentManager.kostyl() {
        if (fragments.size == 0) {
            for (i in 0..backStackEntryCount) {
                popBackStack()
            }
        }
    }

}
