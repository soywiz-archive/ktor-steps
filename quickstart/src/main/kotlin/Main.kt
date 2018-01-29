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
import io.ktor.routing.Route
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
object IndexLocation

@location("/demo")
object DemoLocation

@location("/user/{name}")
data class UserLocation(val name: String)

fun Application.module() {
    install(Locations)
    routing {
        registerCssRoute()
        registerIndexRoute()
        registerDemoRoute()
        registerUserRoute()
    }
}

fun Route.registerCssRoute() {
    static("css") {
        files("css")
    }
}

fun Route.registerIndexRoute() {
    //get("/") {
    get<IndexLocation> {
        call.respondText("Hello, world!", ContentType.Text.Plain)
    }
}

fun Route.registerDemoRoute() {
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
                            a(locations.href(if (n % 2 == 0) DemoLocation else UserLocation("user$n"))) {
                                +"hello"
                            }
                        }
                    }
                }
            }
        }
    }
}

fun Route.registerUserRoute() {
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