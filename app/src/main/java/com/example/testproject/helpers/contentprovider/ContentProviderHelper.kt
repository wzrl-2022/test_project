package com.example.testproject.helpers.contentprovider

import android.content.Context
import android.net.Uri
import android.provider.MediaStore.Images
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ContentProviderHelper @Inject constructor(
    @ApplicationContext val context: Context
) : IContentProviderHelper {
    @Suppress("NAME_SHADOWING")
    override fun getFileName(uri: String): String {
        val uri = Uri.parse(uri)
        return context.contentResolver.query(
            uri,
            arrayOf(Images.ImageColumns.DISPLAY_NAME),
            null,
            null,
            null
        )?.use { cursor ->
            cursor.moveToFirst()
            val columnIndex = cursor.getColumnIndex(Images.ImageColumns.DISPLAY_NAME)
            if (columnIndex != -1) {
                cursor.getString(columnIndex)
            } else {
                ""
            }
        } ?: ""
    }

    @Suppress("NAME_SHADOWING")
    override fun getFileContent(uri: String): ByteArray {
        val uri = Uri.parse(uri)
        return context.contentResolver.openInputStream(uri)?.use { inputStream ->
            inputStream.readBytes()
        } ?: ByteArray(0)
    }
}