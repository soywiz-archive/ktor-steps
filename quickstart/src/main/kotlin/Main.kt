import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.content.files
import io.ktor.content.static
import io.ktor.html.respondHtml
import io.ktor.http.ContentType
import io.ktor.locations.Locations
import io.ktor.locations.get
import io.ktor.locations.location
import io.ktor.locations.locations
import io.ktor.response.respondText
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

@location("/")
class IndexLocation()

@location("/demo")
class DemoLocation()

@location("/user/{name}")
class UserLocation(val name: String)

fun Application.module() {
    install(Locations)
    routing {
        static("css") {
            files("css")
        }
        //get("/") {
        get<IndexLocation> {
            call.respondText("Hello, world!", ContentType.Text.Plain)
        }
        //get("/demo") {
        get<DemoLocation> {
            call.respondHtml {
                head {
                    title("hello")
                }
                body {
                    ul {
                        for (n in 0 until 10) {
                            li {
                                a(locations.href(if (n % 2 == 0) DemoLocation() else UserLocation("user$n"))) {
                                    +"hello"
                                }
                            }
                        }
                    }
                }
            }
        }
        get<UserLocation> { location ->
            call.respondHtml {
                body {
                    h1 {
                        +location.name
                    }
                }
            }
        }
    }
}
