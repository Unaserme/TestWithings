package com.synaone.testwithings.domain.models

interface Image {
    val id: Int

    val previewURL: String

    val tags: String

    val views: Int

    val downloads: Int

    val likes: Int

    val comments: Int

    val user: String

    val userImageURL: String

    val language: List<String>

    val safe: Boolean

    val editorChoice: Boolean

    val category: List<String>

    val colors: List<String>

    val imageType: String?

    val webFormatURL: String
}