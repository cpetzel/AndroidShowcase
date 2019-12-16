package com.petzel.dev.android.androidshowcase.core.rx

import com.uber.autodispose.lifecycle.CorrespondingEventsFunction

enum class CustomScopeEvent {
    ATTACH, DETACH;

    companion object {

        val CORRESPONDING_EVENTS = CorrespondingEventsFunction<CustomScopeEvent> { event ->
            when (event) {
                ATTACH -> DETACH
                else -> {
                    throw RuntimeException("Cannot bind to fragment lifecycle after destroy.")
                }
            }
        }
    }
}
