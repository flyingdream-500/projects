package com.example.contentproviderapp.data.repository

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns._ID
import android.provider.MediaStore
import com.example.contentproviderapp.domain.repository.ILocalImageRepository
import io.reactivex.rxjava3.core.Single

class LocalImageRepositoryImpl(
    private val contentResolver: ContentResolver
) : ILocalImageRepository {

    override fun getLocalImages(): Single<List<Uri>> {
        return Single.fromCallable {
            val result = mutableListOf<Uri>()
            val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(MediaStore.Images.Media._ID)
            val sortOrder = "$_ID DESC"
            contentResolver.query(uri, projection, null, null, sortOrder)?.use { cursor ->
                while (cursor.moveToNext()) {
                    result.add(
                        ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            cursor.getLong(0)
                        )
                    )
                }
            }
            return@fromCallable result
        }
    }
}