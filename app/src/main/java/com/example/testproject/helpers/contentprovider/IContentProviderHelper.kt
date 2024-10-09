package com.example.testproject.helpers.contentprovider

interface IContentProviderHelper {
    fun getFileName(uri: String): String
    fun getFileContent(uri: String): ByteArray
}