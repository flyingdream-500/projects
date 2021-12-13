package com.example.contentproviderapp.presentation.recyclerview.dictionary

import android.animation.Animator
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.contentproviderapp.databinding.ItemDictionaryBinding
import com.example.contentproviderapp.domain.repository.model.DictionaryItemModel


class DictionaryViewHolder(
    private val binding: ItemDictionaryBinding,
    private val deleteDictionary: (Long) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(dictionaryItemModel: DictionaryItemModel) {
        binding.itemKeyword.text = dictionaryItemModel.keyword
        binding.itemTranslate.text = dictionaryItemModel.translation

        Glide.with(itemView)
            .load(Uri.parse(dictionaryItemModel.logo))
            .into(binding.itemLogo)

        itemView.setOnLongClickListener {
            showDelete()

            binding.deleteClose.setOnClickListener {
               deleteCloseClick(dictionaryItemModel.keyword)
            }

            binding.deleteOk.setOnClickListener {
                deleteOkClick(dictionaryItemModel.id)
            }

            return@setOnLongClickListener true
        }
    }



    private fun showDelete() {
        binding.itemKeyword.text = "Удалить?"
        binding.itemTranslate.visibility = View.GONE
        binding.deleteOk.visibility = View.VISIBLE
        binding.deleteClose.visibility = View.VISIBLE
        binding.itemLogo.visibility = View.GONE
        binding.deleting.visibility = View.VISIBLE
    }

    private fun deleteCloseClick(keyword: String) {
        binding.itemKeyword.text = keyword
        binding.itemTranslate.visibility = View.VISIBLE
        binding.deleteOk.visibility = View.GONE
        binding.deleteClose.visibility = View.GONE
        binding.itemLogo.visibility = View.VISIBLE
        binding.deleting.visibility = View.GONE
    }

    private fun deleteOkClick(id: Long) {
        binding.itemKeyword.visibility = View.GONE
        binding.itemTranslate.visibility = View.GONE
        binding.deleteOk.visibility = View.GONE
        binding.deleteClose.visibility = View.GONE
        binding.itemLogo.visibility = View.GONE
        binding.deleting.visibility = View.VISIBLE

        binding.deleting.apply {
            repeatCount = 0
            playAnimation()
            addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    deleteDictionary.invoke(id)
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationRepeat(p0: Animator?) {
                }

            })
        }
    }


    companion object {
        fun create(parent: ViewGroup, deleteDictionary: (Long) -> Unit): DictionaryViewHolder {
            val binding =
                ItemDictionaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return DictionaryViewHolder(binding, deleteDictionary)
        }
    }

}
