package com.synaone.testwithings.core

/**
 * A generic class that holds a value with its loading status.
 *
 * See https://github.com/android/architecture-components-samples/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/vo/Resource.kt
 */
sealed class Resource<out T> {
    companion object {
        /**
         * @return a SUCCESS Resource.
         */
        fun <T> success(data: T): Resource<T> {
            return Success(data)
        }

        /**
         * @return an ERROR Resource, with optional error code.
         */
        fun <T> error(errorCode: Int = 0, errorMessage: String? = null): Resource<T> {
            return Error(errorCode, errorMessage)
        }

        /**
         * @return an ERROR Resource, with optional error code.
         */
        fun <T> loading(): Resource<T> {
            return Loading()
        }
    }
}

/**
 * Resource, for a SUCCESS status.
 */
class Success<out T>(
    /** Result data. */
    val data: T
) : Resource<T>()

/**
 * Resource, for a ERROR status. Can NOT contain result data.
 */
class Error<out T>(
    /** Error code. */
    val errorCode: Int,

    /** Potential error message. */
    val errorMessage: String? = null
) : Resource<T>()

class Loading<out T> : Resource<T>()