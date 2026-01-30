package net.fishao.server.amf3

import java.io.DataInputStream
import java.io.InputStream
import java.nio.charset.StandardCharsets
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

class AMF3Deserializer(
    inputStream: InputStream,
    private val maxStringLength: Int = 1_000_000,
    private val maxArraySize: Int = 100_000,
    private val maxObjectRefs: Int = 10_000,
    private val maxRecursionDepth: Int = 100
) {

    private val input = DataInputStream(inputStream)

    private val stringReferences = ArrayList<String>()
    private val objectReferences = ArrayList<Any>()
    private val traitReferences = ArrayList<TraitInfo>()

    private var recursionDepth = 0

    private val classAliases = mutableMapOf<String, KClass<*>>()

    fun registerAlias(alias: String, kClass: KClass<*>) {
        classAliases[alias] = kClass
    }

    fun readObject(): Any? {
        if (++recursionDepth > maxRecursionDepth) {
            throw SecurityException("Max recursion depth ($maxRecursionDepth) exceeded")
        }

        try {
            return when (val type = input.readByte().toInt()) {
                0x00 -> undefined
                0x01 -> null
                0x02 -> false
                0x03 -> true
                0x04 -> readInt29()
                0x05 -> input.readDouble()
                0x06 -> readString()
                0x07 -> readXml()
                0x08 -> readDate()
                0x09 -> readArray()
                0x0A -> readScriptObject()
                0x0B -> readXml()
                0x0C -> readByteArray()
                else -> throw IllegalArgumentException("Unknown AMF3 type marker: $type")
            }
        } finally {
            recursionDepth--
        }
    }


    private fun readScriptObject(): Any {
        val ref = readUInt29()

        if ((ref and 1) == 0) {
            return getObjectReference(ref shr 1)
        }

        val traits = readTraits(ref)

        val instance: Any
        val isTyped: Boolean

        if (traits.className.isNotEmpty() && classAliases.containsKey(traits.className)) {
            val kClass = classAliases[traits.className]
                ?: throw IllegalStateException("Class alias not found: ${traits.className}")
            instance = kClass.createInstance()
            isTyped = true
        } else {
            println("Warning: No class registered for AMF3 type '${traits.className}', using dynamic object")
            instance = HashMap<String, Any?>()
            isTyped = false
        }

        addObjectReference(instance)

        traits.properties.forEach { propName ->
            val value = readObject()
            applyProperty(instance, isTyped, propName, value)
        }

        if (traits.isDynamic) {
            var dynamicCount = 0
            while (true) {
                val name = readString()
                if (name.isEmpty()) break

                if (++dynamicCount > maxArraySize) {
                    throw SecurityException("Too many dynamic properties")
                }

                val value = readObject()
                applyProperty(instance, isTyped, name, value)
            }
        }

        return instance
    }

    private fun applyProperty(instance: Any, isTyped: Boolean, name: String, value: Any?) {
        if (!isTyped) {
            (instance as MutableMap<String, Any?>)[name] = value
        } else {
            try {
                val kClass = instance::class
                val prop = kClass.memberProperties.firstOrNull { it.name == name }
                val field = prop?.javaField

                if (field != null) {
                    field.isAccessible = true
                    val safeValue = convertType(value, field.type)
                    field.set(instance, safeValue)
                }
            } catch (e: IllegalAccessException) {
                throw SecurityException("Cannot access property '$name' on ${instance::class.simpleName}", e)
            } catch (e: IllegalArgumentException) {
                System.err.println("Warning: Could not set property '$name' on ${instance::class.simpleName}: ${e.message}")
            }
        }
    }

    private fun convertType(value: Any?, targetType: Class<*>): Any? {
        if (value is Number) {
            return when (targetType) {
                Int::class.java, Integer::class.java -> value.toInt()
                Long::class.java, java.lang.Long::class.java -> value.toLong()
                Short::class.java, java.lang.Short::class.java -> value.toShort()
                Float::class.java, java.lang.Float::class.java -> value.toFloat()
                Byte::class.java, java.lang.Byte::class.java -> value.toByte()
                else -> value
            }
        }
        return value
    }

    private fun readTraits(ref: Int): TraitInfo {
        if ((ref and 3) == 1) {
            return getTraitReference(ref shr 2)
        }

        val isExternalizable = (ref and 4) == 4
        val isDynamic = (ref and 8) == 8
        val count = ref shr 4

        if (count < 0 || count > maxArraySize) {
            throw IllegalArgumentException("Invalid trait property count: $count")
        }

        val className = readString()

        val traits = TraitInfo(className, isDynamic, isExternalizable, ArrayList())
        addTraitReference(traits)

        for (i in 0 until count) {
            traits.properties.add(readString())
        }
        return traits
    }

    private fun readString(): String {
        val ref = readUInt29()
        if ((ref and 1) == 0) {
            return getStringReference(ref shr 1)
        }
        val len = ref shr 1

        if (len < 0 || len > maxStringLength) {
            throw IllegalArgumentException("Invalid string length: $len (max: $maxStringLength)")
        }

        if (len == 0) return ""

        val bytes = ByteArray(len)
        input.readFully(bytes)
        val str = String(bytes, StandardCharsets.UTF_8)
        addStringReference(str)
        return str
    }

    private fun readInt29(): Int {
        val i = readUInt29()
        return if (i > 0x0FFFFFFF) (i shl 3) shr 3 else i
    }

    private fun readUInt29(): Int {
        var b = input.readByte().toInt() and 0xFF
        if (b < 128) return b
        var value = (b and 0x7F) shl 7
        b = input.readByte().toInt() and 0xFF
        if (b < 128) return value or b
        value = (value or (b and 0x7F)) shl 7
        b = input.readByte().toInt() and 0xFF
        if (b < 128) return value or b
        value = (value or (b and 0x7F)) shl 8
        b = input.readByte().toInt() and 0xFF
        return value or b
    }

    private fun readArray(): Any {
        val ref = readUInt29()
        if ((ref and 1) == 0) {
            return getObjectReference(ref shr 1)
        }

        val len = ref shr 1

        if (len < 0 || len > maxArraySize) {
            throw IllegalArgumentException("Invalid array length: $len (max: $maxArraySize)")
        }

        val map = HashMap<String, Any?>()
        addObjectReference(map)

        var assocCount = 0
        while (true) {
            val name = readString()
            if (name.isEmpty()) break

            if (++assocCount > maxArraySize) {
                throw SecurityException("Too many associative array keys")
            }

            map[name] = readObject()
        }

        val list = ArrayList<Any?>()
        for (i in 0 until len) {
            list.add(readObject())
        }

        return if (map.isEmpty()) list else {
            for (i in list.indices) map[i.toString()] = list[i]
            map
        }
    }

    private fun readDate(): java.util.Date {
        val ref = readUInt29()
        if ((ref and 1) == 0) {
            val obj = getObjectReference(ref shr 1)
            return obj as? java.util.Date
                ?: throw IllegalStateException("Expected Date but got ${obj::class.simpleName}")
        }
        val ms = input.readDouble()

        if (!ms.isFinite() || ms < -62135596800000.0 || ms > 253402300799999.0) {
            throw IllegalArgumentException("Invalid date timestamp: $ms")
        }

        val date = java.util.Date(ms.toLong())
        addObjectReference(date)
        return date
    }

    private fun readXml(): String {
        val ref = readUInt29()
        if ((ref and 1) == 0) {
            val obj = getObjectReference(ref shr 1)
            return obj as? String
                ?: throw IllegalStateException("Expected String but got ${obj::class.simpleName}")
        }
        val len = ref shr 1

        if (len < 0 || len > maxStringLength) {
            throw IllegalArgumentException("Invalid XML length: $len (max: $maxStringLength)")
        }

        val bytes = ByteArray(len)
        input.readFully(bytes)
        val str = String(bytes, StandardCharsets.UTF_8)
        addObjectReference(str)
        return str
    }

    private fun readByteArray(): ByteArray {
        val ref = readUInt29()
        if ((ref and 1) == 0) {
            val obj = getObjectReference(ref shr 1)
            return obj as? ByteArray
                ?: throw IllegalStateException("Expected ByteArray but got ${obj::class.simpleName}")
        }
        val len = ref shr 1

        if (len < 0 || len > maxStringLength) {
            throw IllegalArgumentException("Invalid ByteArray length: $len (max: $maxStringLength)")
        }

        val ba = ByteArray(len)
        input.readFully(ba)
        addObjectReference(ba)
        return ba
    }

    private fun getStringReference(index: Int): String {
        if (index < 0 || index >= stringReferences.size) {
            throw IllegalArgumentException("Invalid string reference: $index (size: ${stringReferences.size})")
        }
        return stringReferences[index]
    }

    private fun getObjectReference(index: Int): Any {
        if (index < 0 || index >= objectReferences.size) {
            throw IllegalArgumentException("Invalid object reference: $index (size: ${objectReferences.size})")
        }
        return objectReferences[index]
    }

    private fun getTraitReference(index: Int): TraitInfo {
        if (index < 0 || index >= traitReferences.size) {
            throw IllegalArgumentException("Invalid trait reference: $index (size: ${traitReferences.size})")
        }
        return traitReferences[index]
    }

    private fun addStringReference(str: String) {
        if (stringReferences.size >= maxObjectRefs) {
            throw SecurityException("String reference limit exceeded: $maxObjectRefs")
        }
        stringReferences.add(str)
    }

    private fun addObjectReference(obj: Any) {
        if (objectReferences.size >= maxObjectRefs) {
            throw SecurityException("Object reference limit exceeded: $maxObjectRefs")
        }
        objectReferences.add(obj)
    }

    private fun addTraitReference(trait: TraitInfo) {
        if (traitReferences.size >= maxObjectRefs) {
            throw SecurityException("Trait reference limit exceeded: $maxObjectRefs")
        }
        traitReferences.add(trait)
    }

    data class TraitInfo(
        val className: String,
        val isDynamic: Boolean,
        val isExternalizable: Boolean,
        val properties: MutableList<String>
    )

    companion object {
        val undefined = Any()
    }
}