package ui.profile.edit.models

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import models.profile.ResearchWorkDescription

class ResearchWorkDescriptionEdit(
    researchWorkDescription: ResearchWorkDescription
) {
    val name = mutableStateOf(TextFieldValue(researchWorkDescription.name))
    val description = mutableStateOf(TextFieldValue(researchWorkDescription.description))
    val detailsURL = mutableStateOf(TextFieldValue(researchWorkDescription.detailsURL))

    fun toResearchWorkDescription(): ResearchWorkDescription =
        ResearchWorkDescription(
            name = name.value.text,
            description = description.value.text,
            detailsURL = detailsURL.value.text
        )
}