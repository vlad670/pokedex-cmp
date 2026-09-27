package band.effective.education.crossplatform.data

import kotlinx.serialization.Serializable

@Serializable
data class UniProtSearchResponse(
    val results: List<UniProtEntryDto> = emptyList()
)
@Serializable
data class UniProtEntryDto(
    val primaryAccession: String,
    val uniProtkbId: String? = null,
    val proteinDescription: ProteinDescriptionDto? = null,
    val genes: List<GeneDto>? = null,
    val organism: OrganismDto? = null,
    val sequence: SequenceDto? = null
)

@Serializable
data class ProteinDescriptionDto(val recommendedName: RecommendedNameDto? = null)

@Serializable
data class RecommendedNameDto(val fullName: ValueDto? = null)

@Serializable
data class ValueDto(val value: String)

@Serializable
data class GeneDto(val geneName: ValueDto? = null)

@Serializable
data class OrganismDto(val scientificName: String? = null, val commonName: String? = null)

@Serializable
data class SequenceDto(val value: String? = null, val length: Int? = null)
fun UniProtEntryDto.toProtein(): Protein = Protein(
    primaryAccession = primaryAccession,
    entryName = uniProtkbId ?: primaryAccession,
    proteinName = proteinDescription?.recommendedName?.fullName?.value ?: "Unknown",
    organismName = buildString {
        append(organism?.scientificName ?: "Unknown")
        organism?.commonName?.let { append(" ($it)") }
    },
    geneName = genes?.firstOrNull()?.geneName?.value,
    sequence = sequence?.value
)