package net.fishao.server.amf3

import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.nio.charset.StandardCharsets
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

class AMF3Serializer {

    private val output = ByteArrayOutputStream()
    private val dataOutput = DataOutputStream(output)

    private val stringReferences = mutableListOf<String>()
    private val objectReferences = mutableListOf<Any>()
    private val traitReferences = mutableListOf<TraitInfo>()

    private val classAliases = mutableMapOf<KClass<*>, String>()

    fun registerAlias(kClass: KClass<*>, alias: String) {
        classAliases[kClass] = alias
    }

    fun writeObject(obj: Any?) {
        when (obj) {
            null -> dataOutput.writeByte(0x01)
            is Boolean -> {
                dataOutput.writeByte(if (obj) 0x03 else 0x02)
            }

            is Int -> {
                dataOutput.writeByte(0x04)
                writeInt29(obj)
            }

            is Long -> {
                dataOutput.writeByte(0x05)
                dataOutput.writeDouble(obj.toDouble())
            }

            is Double -> {
                dataOutput.writeByte(0x05)
                dataOutput.writeDouble(obj)
            }

            is Float -> {
                dataOutput.writeByte(0x05)
                dataOutput.writeDouble(obj.toDouble())
            }

            is String -> {
                dataOutput.writeByte(0x06)
                writeString(obj)
            }

            is java.util.Date -> {
                dataOutput.writeByte(0x08)
                writeDate(obj)
            }

            is List<*> -> {
                dataOutput.writeByte(0x09)
                writeArray(obj)
            }

            is Map<*, *> -> {
                dataOutput.writeByte(0x09)
                writeAssociativeArray(obj)
            }

            is ByteArray -> {
                dataOutput.writeByte(0x0C)
                writeByteArray(obj)
            }

            else -> {
                dataOutput.writeByte(0x0A)
                writeScriptObject(obj)
            }
        }
    }

    private fun writeScriptObject(obj: Any) {
        val refIndex = objectReferences.indexOf(obj)
        if (refIndex != -1) {
            writeUInt29(refIndex shl 1)
            return
        }

        objectReferences.add(obj)

        val kClass = obj::class
        val alias = classAliases[kClass] ?: ""

        val properties = kClass.memberProperties.mapNotNull { prop ->
            val field = prop.javaField
            if (field != null) {
                field.isAccessible = true
                prop.name to field.get(obj)
            } else null
        }

        val traitInfo = TraitInfo(
            className = alias,
            isDynamic = false,
            isExternalizable = false,
            properties = properties.map { it.first }.toMutableList()
        )

        writeTraits(traitInfo)

        properties.forEach { (_, value) ->
            writeObject(value)
        }
    }

    private fun writeTraits(traits: TraitInfo) {
        val refIndex = traitReferences.indexOfFirst {
            it.className == traits.className && it.properties == traits.properties
        }

        if (refIndex != -1) {
            writeUInt29((refIndex shl 2) or 0x01)
            return
        }

        traitReferences.add(traits)

        var ref = 0x03

        if (traits.isExternalizable) {
            ref = ref or 0x04
        }

        if (traits.isDynamic) {
            ref = ref or 0x08
        }

        ref = ref or (traits.properties.size shl 4)

        writeUInt29(ref)

        writeString(traits.className)

        traits.properties.forEach { propName ->
            writeString(propName)
        }
    }

    private fun writeString(str: String) {
        if (str.isEmpty()) {
            writeUInt29(0x01)
            return
        }

        val refIndex = stringReferences.indexOf(str)
        if (refIndex != -1) {
            writeUInt29(refIndex shl 1)
            return
        }

        stringReferences.add(str)

        val bytes = str.toByteArray(StandardCharsets.UTF_8)
        writeUInt29((bytes.size shl 1) or 0x01)
        dataOutput.write(bytes)
    }

    private fun writeInt29(value: Int) {
        val v = if (value < 0) {
            (value and 0x1FFFFFFF)
        } else {
            value
        }

        writeUInt29(v)
    }

    private fun writeUInt29(value: Int) {
        when {
            value < 0x80 -> {
                dataOutput.writeByte(value)
            }

            value < 0x4000 -> {
                dataOutput.writeByte(((value shr 7) and 0x7F) or 0x80)
                dataOutput.writeByte(value and 0x7F)
            }

            value < 0x200000 -> {
                dataOutput.writeByte(((value shr 14) and 0x7F) or 0x80)
                dataOutput.writeByte(((value shr 7) and 0x7F) or 0x80)
                dataOutput.writeByte(value and 0x7F)
            }

            else -> {
                dataOutput.writeByte(((value shr 22) and 0x7F) or 0x80)
                dataOutput.writeByte(((value shr 15) and 0x7F) or 0x80)
                dataOutput.writeByte(((value shr 8) and 0x7F) or 0x80)
                dataOutput.writeByte(value and 0xFF)
            }
        }
    }

    private fun writeArray(list: List<*>) {
        val refIndex = objectReferences.indexOf(list)
        if (refIndex != -1) {
            writeUInt29(refIndex shl 1)
            return
        }

        objectReferences.add(list)

        writeUInt29((list.size shl 1) or 0x01)

        writeString("")

        list.forEach { writeObject(it) }
    }

    private fun writeAssociativeArray(map: Map<*, *>) {
        val refIndex = objectReferences.indexOf(map)
        if (refIndex != -1) {
            writeUInt29(refIndex shl 1)
            return
        }

        objectReferences.add(map)

        writeUInt29(0x01)

        map.forEach { (key, value) ->
            writeString(key.toString())
            writeObject(value)
        }

        writeString("")
    }

    private fun writeDate(date: java.util.Date) {
        val refIndex = objectReferences.indexOf(date)
        if (refIndex != -1) {
            writeUInt29(refIndex shl 1)
            return
        }

        objectReferences.add(date)

        writeUInt29(0x01)

        dataOutput.writeDouble(date.time.toDouble())
    }

    private fun writeByteArray(bytes: ByteArray) {
        val refIndex = objectReferences.indexOf(bytes)
        if (refIndex != -1) {
            writeUInt29(refIndex shl 1)
            return
        }

        objectReferences.add(bytes)

        writeUInt29((bytes.size shl 1) or 0x01)

        dataOutput.write(bytes)
    }

    fun toByteArray(): ByteArray {
        dataOutput.flush()
        return output.toByteArray()
    }

    fun reset() {
        output.reset()
        stringReferences.clear()
        objectReferences.clear()
        traitReferences.clear()
    }

    data class TraitInfo(
        val className: String,
        val isDynamic: Boolean,
        val isExternalizable: Boolean,
        val properties: MutableList<String>
    )
}