import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.html.*

fun main(args: Array<String>) {
    val server = embeddedServer(Netty, 8080) {
        routing {
            get("/") {
                call.respondText("Hello, world!", ContentType.Text.Plain)
            }
            get("/demo") {
                call.respondHtml {
                    head {
                        title("hello")
                    }
                    body {
                        ul {
                            for (n in 0 until 10) {
                                li {
                                    +"hello"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    server.start(wait = true)
}

