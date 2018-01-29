import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlin.reflect.jvm.internal.impl.renderer.RenderingFormat

fun main(args: Array<String>) {
    val server = embeddedServer(Netty, 8080) {
        routing {
            get("/") {
                call.respondText("Hello, world!", ContentType.Text.Plain)
            }
            get("/demo") {
                call.respondText { "HELLO WORLD!" }
            }
        }
    }
    server.start(wait = true)
}

