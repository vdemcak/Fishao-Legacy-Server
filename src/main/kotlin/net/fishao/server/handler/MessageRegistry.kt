package net.fishao.server.handler

import net.fishao.server.amf3.AMF3Deserializer
import net.fishao.server.protocol.cr.ClientRequest
import net.fishao.server.protocol.sr.ServerResp
import kotlin.reflect.KClass

/**
 * Central registry for message handlers and AMF3 type aliases.
 * Manages the mapping between message types, handlers, and AMF3 aliases.
 */
class MessageRegistry {
    private val handlers = mutableMapOf<Class<*>, MessageHandler<*>>()
    private val requestAliases = mutableMapOf<String, KClass<*>>()
    private val responseAliases = mutableMapOf<KClass<*>, String>()

    /**
     * Register a message handler with its AMF3 alias
     */
    fun <T : ClientRequest> registerHandler(alias: String, handler: MessageHandler<T>) {
        val requestClass = handler.requestClass
        handlers[requestClass] = handler
        requestAliases[alias] = requestClass.kotlin
    }

    /**
     * Register a response type alias for serialization
     */
    fun registerResponseAlias(kClass: KClass<out ServerResp>, alias: String) {
        responseAliases[kClass] = alias
    }

    /**
     * Get the handler for a specific request type
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : ClientRequest> getHandler(requestClass: Class<T>): MessageHandler<T>? {
        return handlers[requestClass] as? MessageHandler<T>
    }

    /**
     * Get the AMF3 alias for a response class
     */
    fun getResponseAlias(kClass: KClass<*>): String? {
        return responseAliases[kClass]
    }

    /**
     * Configure a deserializer with all registered request aliases
     */
    fun configureDeserializer(deserializer: AMF3Deserializer) {
        requestAliases.forEach { (alias, kClass) ->
            deserializer.registerAlias(alias, kClass)
        }
    }
}
