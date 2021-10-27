package ui.utils

import models.profile.Status

fun statusToString(status: Status) =
    when (status) {
        Status.ACTIVE -> "Active"
        Status.NON_ACTIVE -> "Non active"
    }