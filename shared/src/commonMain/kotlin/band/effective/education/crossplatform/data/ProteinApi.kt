package band.effective.education.crossplatform.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.compression.ContentEncoding


class ProteinApi {
    private val client = HttpClient {
        install(ContentEncoding) {
            gzip()
            deflate()
        }
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    /** Возвращает список белков и общее число найденных (из заголовка x-total-results). */
    suspend fun fetchProteins(): Pair<List<Protein>, Int> {
        val response: HttpResponse = client.get("https://rest.uniprot.org/uniprotkb/search") {
            parameter("query", "accession:P01308 OR accession:P68871 OR accession:P00533")
            parameter("format", "json")
        }

        val total = response.headers["x-total-results"]?.toIntOrNull() ?: 0
        val body: UniProtSearchResponse = response.body()

        return body.results.map { it.toProtein() } to total
    }
}