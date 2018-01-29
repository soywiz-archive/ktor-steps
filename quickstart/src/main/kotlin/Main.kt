import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ConditionalHeaders
import io.ktor.html.respondHtml
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.html.*

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080) {
        module()
    }.apply {
        start(wait = true)
    }

}

fun Application.module() {
    //install(Routing) {
    //}
    install(ConditionalHeaders) {

    }

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
                                a("..") {
                                    +"hello"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}