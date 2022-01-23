package com.synaone.testwithings.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.synaone.testwithings.data.local.Converters
import com.synaone.testwithings.domain.models.Image


@Entity(tableName = "images")
@TypeConverters(Converters::class)
data class ImageEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    override val id: Int,

    @ColumnInfo(name = "previewURL")
    override val previewURL: String,

    @ColumnInfo(name = "tags")
    override val tags: String,

    @ColumnInfo(name = "views")
    override val views: Int,

    @ColumnInfo(name = "downloads")
    override val downloads: Int,

    @ColumnInfo(name = "likes")
    override val likes: Int,

    @ColumnInfo(name = "comments")
    override val comments: Int,

    @ColumnInfo(name = "user")
    override val user: String,

    @ColumnInfo(name = "userImageURL")
    override val userImageURL: String,

    @ColumnInfo(name = "category")
    override val category: List<String>,

    @ColumnInfo(name = "colors")
    override val colors: List<String>,

    @ColumnInfo(name = "editorChoice")
    override val editorChoice: Boolean,

    @ColumnInfo(name = "language")
    override val language: List<String>,

    @ColumnInfo(name = "safe")
    override val safe: Boolean,

    @ColumnInfo(name = "imageType")
    override val imageType: String?,

    @ColumnInfo(name = "webFormatURL")
    override val webFormatURL: String

): Image {

    fun isSameAs(imageEntity: ImageEntity): Boolean {
        return id == imageEntity.id
                && previewURL == imageEntity.previewURL
                && tags == imageEntity.tags
                && views == imageEntity.views
                && downloads == imageEntity.downloads
                && likes == imageEntity.likes
                && comments == imageEntity.comments
                && user == imageEntity.user
                && userImageURL == imageEntity.userImageURL
                && category.containsAll(imageEntity.category)
                && colors.containsAll(imageEntity.colors)
                && editorChoice == imageEntity.editorChoice
                && language.containsAll(imageEntity.language)
                && safe == imageEntity.safe
                && imageType == imageEntity.imageType
                && webFormatURL == imageEntity.webFormatURL
    }
}