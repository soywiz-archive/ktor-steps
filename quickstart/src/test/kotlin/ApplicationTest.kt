import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.junit.Assert.assertEquals
import org.junit.Test

class ApplicationTest {
    @Test
    fun testRequest() = withTestApplication({
        routing {
            get("/") {
                call.respondText { "HELLO WORLD!" }
            }
        }
    }) {
        with(handleRequest(HttpMethod.Get, "/")) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals("HELLO WORLD!", response.content)
        }
    }
}
