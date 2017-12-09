package com.jux.ouiclashroyale.ui.common.livedata

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer

/**
 * A SingleLiveEvent used for Snackbar messages.
 */
class SnackbarMessage : SingleLiveEvent<String>() {

    fun observe(owner: LifecycleOwner, observer: SnackbarObserver) {
        super.observe(owner, Observer {
            if (it == null) return@Observer
            observer.onNewMessage(it)
        })
    }

    /**
     * A callback interface to notify that there is a new message to be displayed
     */
    interface SnackbarObserver {
        fun onNewMessage(message: String)
    }
}