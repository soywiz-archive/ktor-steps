import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.withCharset
import io.ktor.server.testing.contentType
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.junit.Assert.assertEquals
import org.junit.Test

class ApplicationTest {
    @Test
    fun testRequest() = withTestApplication({
        module()
    }) {
        with(handleRequest(HttpMethod.Get, "/")) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals("Hello, world!", response.content)
        }
        with(handleRequest(HttpMethod.Get, "/css/styles.css")) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(ContentType.Text.CSS.withCharset(Charsets.UTF_8), response.contentType())
        }
    }
}
