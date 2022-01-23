package com.synaone.testwithings.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.OnConflictStrategy
import com.synaone.testwithings.data.local.models.ImageEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Dao
interface ImagesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertImage(image: ImageEntity): Long

    @Query("""SELECT * FROM images where id = :id""")
    suspend fun retrieveImage(id: Int): ImageEntity?

    @Transaction
    suspend fun insertOrUpdateImages(images: List<ImageEntity>) {

        images.forEach { image ->

            val id = insertImage(image)

            if (id == -1L) {
                retrieveImage(image.id)?.let { savedImage ->
                    if (!savedImage.isSameAs(image)) {
                        updateImage(
                            id = image.id,
                            safe = image.safe || savedImage.safe,
                            language = Json.encodeToString((image.language + savedImage.language).distinct()),
                            webFormatURL = image.previewURL,
                            tags = image.tags,
                            views = image.views,
                            downloads = image.downloads,
                            likes = image.downloads,
                            comments = image.downloads,
                            user = image.user,
                            userImageURL = image.userImageURL,
                            category = Json.encodeToString((image.category + savedImage.category).distinct()),
                            colors = Json.encodeToString((image.colors + savedImage.colors).distinct()),
                            editorChoice = image.editorChoice || savedImage.editorChoice,
                            imageType = image.imageType ?: savedImage.imageType,
                            previewURL = image.previewURL
                        )
                    }
                }
            }
        }
    }


    @Query("""UPDATE images SET 
        webFormatURL = :webFormatURL,
        tags = :tags,
        views = :views,
        downloads = :downloads,
        likes = :likes,
        comments = :comments,
        user = :user,
        userImageURL = :userImageURL,
        category = :category,
        colors = :colors,
        editorChoice = :editorChoice,
        language = :language,
        safe = :safe, 
        imageType = :imageType,
        previewURL = :previewURL
        WHERE id = :id
    """)
    suspend fun updateImage(
        id: Int,
        safe: Boolean,
        language: String,
        webFormatURL: String,
        previewURL: String,
        tags: String,
        views: Int,
        downloads: Int,
        likes: Int,
        comments: Int,
        user: String,
        userImageURL: String,
        category: String,
        colors: String,
        editorChoice: Boolean,
        imageType: String?
    )

    @Query("""SELECT * FROM images 
        where safe = :isSafeSearchEnable 
        and editorChoice = :isEditorChoiceEnable 
        and language like :selectedLanguage 
        and (category like :selectedCategory or :selectedCategory is null)
        and colors like :selectedColor
        and (imageType = :selectedImageType or :selectedImageType is null)
        order by likes desc""")
    fun getPopularImages(
        isSafeSearchEnable: Boolean,
        isEditorChoiceEnable: Boolean,
        selectedLanguage: String,
        selectedCategory: String?,
        selectedColor: String,
        selectedImageType: String?,
    ): Flow<List<ImageEntity>>

    @Query("""SELECT * FROM images 
        where safe = :isSafeSearchEnable
        and editorChoice = :isEditorChoiceEnable 
        and language like :selectedLanguage 
        and (category = :selectedCategory or :selectedCategory is null)
        and colors like :selectedColor
        and (imageType = :selectedImageType or :selectedImageType is null)
        order by id desc""")
    fun getLatestImages(
        isSafeSearchEnable: Boolean,
        isEditorChoiceEnable: Boolean,
        selectedLanguage: String,
        selectedCategory: String?,
        selectedColor: String,
        selectedImageType: String?,
    ): Flow<List<ImageEntity>>

    @Query("""SELECT * FROM images where id in (:favoriteId)""")
    fun getImages(
        favoriteId: List<Int>
    ): Flow<List<ImageEntity>>
}
