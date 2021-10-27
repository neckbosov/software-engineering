package ui.profile.edit.models

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import models.profile.JobDescription

class JobDescriptionEdit(
    jobDescription: JobDescription
) {
    val place = mutableStateOf(TextFieldValue(jobDescription.place))
    val position = mutableStateOf(TextFieldValue(jobDescription.position))
    val period1 = mutableStateOf(TextFieldValue(jobDescription.period.first))
    val period2 = mutableStateOf(TextFieldValue(jobDescription.period.second))

    fun toJobDescription(): JobDescription =
        JobDescription(
            place = place.value.text,
            position = position.value.text,
            period = Pair(period1.value.text, period2.value.text)
        )
}