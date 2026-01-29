package net.fishao.server.transport

import net.fishao.server.handler.MessageRegistry
import net.fishao.server.handler.MessageRouter

/**
 * Abstract base for different transport implementations.
 */
abstract class Transport(
    protected val registry: MessageRegistry,
    protected val router: MessageRouter
)
