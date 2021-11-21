package ui.profile.edit.models

import androidx.compose.runtime.mutableStateOf
import models.profile.JobDescription

class JobDescriptionEdit(
    jobDescription: JobDescription
) {
    val place = mutableStateOf(jobDescription.place)
    val position = mutableStateOf(jobDescription.position)
    val period1 = mutableStateOf(jobDescription.period.first)
    val period2 = mutableStateOf(jobDescription.period.second)

    fun toJobDescription(): JobDescription =
        JobDescription(
            place = place.value,
            position = position.value,
            period = Pair(period1.value, period2.value)
        )
}