package com.aero51.moviedatabase.utils

/**
 * Status of a resource that is provided to the UI.
 *
 *
 * These are usually created by the Repository classes where they return
 * `LiveData<Resource></Resource><T>>` to pass back the latest data to the UI with its fetch status.
</T> */
enum class Status {
    SUCCESS, ERROR, LOADING
}