package cache

import kotlinx.html.HTMLTag
import kotlinx.html.HtmlBlockTag
import kotlinx.html.Tag
import kotlinx.html.TagConsumer
import kotlinx.html.stream.appendHTML
import java.io.StringWriter

class HtmlCache(override val consumer: TagConsumer<*>) : HTMLTag("cache", consumer, mapOf(), null, false, false),
    HtmlBlockTag

interface Cache {
    fun get(key: String, producer: (key: String) -> String): String
}

class InmemoryCache : Cache {
    private val keys = hashMapOf<String, String>()

    // @TODO: Handle concurrency and wait for results to be generated
    override fun get(key: String, producer: (key: String) -> String): String {
        return keys.getOrPut(key) {
            producer(key)
        }
    }
}

fun Tag.cache(cache: Cache, key: String, block: HtmlCache.() -> Unit = {}) {
    val content = cache.get(key) {
        val writer = StringWriter()
        writer.use {
            val consumer = it.appendHTML()
            block(HtmlCache(consumer))
        }
        writer.toString()
    }

    consumer.onTagContentUnsafe { +content }
}
