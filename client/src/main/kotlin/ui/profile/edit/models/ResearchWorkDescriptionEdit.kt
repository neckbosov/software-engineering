package ui.profile.edit.models

import androidx.compose.runtime.mutableStateOf
import models.profile.ResearchWorkDescription

class ResearchWorkDescriptionEdit(
    researchWorkDescription: ResearchWorkDescription
) {
    val name = mutableStateOf(researchWorkDescription.name)
    val description = mutableStateOf(researchWorkDescription.description)
    val detailsURL = mutableStateOf(researchWorkDescription.detailsURL)

    fun toResearchWorkDescription(): ResearchWorkDescription =
        ResearchWorkDescription(
            name = name.value,
            description = description.value,
            detailsURL = detailsURL.value
        )
}