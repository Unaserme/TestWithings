package com.synaone.testwithings.data.remote.models

/**
 * Network related error codes.
 */

const val ERROR_UNKNOWN: Int = -1
const val ERROR_SOCKET_TIMEOUT: Int = -2

/** 1xxx range is reserved for HTTP error codes. */
const val HTTP_ERROR_BASE: Int = 1000