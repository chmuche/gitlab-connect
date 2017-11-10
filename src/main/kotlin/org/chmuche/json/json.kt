package org.chmuche.json

import com.beust.klaxon.*
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import java.io.StringReader
import java.math.BigInteger
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.collections.HashMap
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

abstract class JsonResourceCompanion<out T : JsonRessource>(val baseUrl: String) {

    fun new(json: String) = new(parseObj(json))
    fun empty() = new(JsonObject(emptyMap()))

    private fun parseObj(name: String) =
            if (name.isBlank()) JsonObject()
            else Parser().parse(StringReader(name)) as JsonObject

    abstract fun new(obj: JsonObject): T

    operator fun get(id: Any): T {
        var glProject = empty()
        val (request, response, result) = "$baseUrl/$id".httpGet().responseString()
        val (bytes, error) = result
        if (bytes != null) {
            glProject = new(bytes)
        }
        return glProject
    }

    fun getList(params: List<Pair<String, Any>> = emptyList()): Collection<T> {
        var glProject = mutableListOf<T>()
        val (request, response, result) = baseUrl.httpGet(params).responseString()
        val (bytes, error) = result
        if (bytes != null) {
            for (array in Parser().parse(StringReader(bytes)) as JsonArray<*>) {
                if (array is JsonObject) {
                    glProject.add(new(array))
                }
            }
        }
        return glProject

    }
}

abstract class SubJsonResourceCompanion
<out T : SubJsonResource, out PC : JsonResourceCompanion<*>>
(parent: PC, baseUrl: String) : JsonResourceCompanion<T>("${parent.baseUrl}/$baseUrl") {
    private val parentPath = parent.baseUrl
    private val internPath = baseUrl

    fun getListByParent(idParent: String, params: List<Pair<String, Any>> = emptyList()): Collection<T> {
        var glProject = mutableListOf<T>()
        val (request, response, result) = "$parentPath/$idParent/$internPath".httpGet(params).responseString()
        val (bytes, error) = result
        if (bytes != null) {
            for (array in Parser().parse(StringReader(bytes)) as JsonArray<*>) {
                if (array is JsonObject) {
                    glProject.add(new(array))
                }
            }
        }
        return glProject
    }

    fun create(idParent: String, comp: T.() -> Unit): T {
        val newObj = empty()
        newObj.comp()
        val (request, response, result) = "$parentPath/$idParent/$internPath".httpPost()
                .body(newObj.toString()).responseString()
        val (bytes, error) = result
        if (error != null){
            throw IllegalArgumentException()
        }
        if (bytes != null) {
            return new(bytes)
        }
        return empty()
    }

    fun delete(idParent: String, id: String) {
        val (request, response, result) = "$parentPath/$idParent/$internPath/id".httpDelete().responseString()
        val (bytes, error) = result
        if (error != null){
            throw IllegalArgumentException()
        }
    }
}

open class JsonRessource(private val obj: JsonObject) {
    fun jsonStr(key: String, defaultValue: String = "") = JsonString(defaultValue)
    fun <E : Enum<E>> jsonEnum(key: String, enum: KClass<E>) = JsonEnum(enum)
    fun jsonInt(key: String, defaultValue: Int = 0) = JsonInt(defaultValue)
    fun jsonFloat(key: String, defaultValue: Float = 0F) = JsonFloat(defaultValue)
    fun jsonLong(key: String, defaultValue: Long = 0L) = JsonLong(defaultValue)
    fun jsonDouble(key: String, defaultValue: Double = 0.toDouble()) = JsonDouble(defaultValue)
    fun jsonBigInt(key: String, defaultValue: BigInteger = BigInteger.ZERO) = JsonBigInt(defaultValue)
    fun jsonBoolean(key: String, defaultValue: Boolean = false) = JsonBoolean(defaultValue)
    fun jsonDate(key: String, format: DateTimeFormatter = DateTimeFormatter.ISO_DATE) = JsonDate(format)
    fun jsonDateTime(key: String, format: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME) = JsonDateTime(format)
    fun jsonTime(key: String, format: DateTimeFormatter = DateTimeFormatter.ISO_TIME) = JsonTime(format)
    fun <E : Any> jsonList(key: String, type: KClass<E>) = JsonList<E>()
    fun <T : JsonRessource> jsonObj(key: String, type: JsonResourceCompanion<T>) = JsonRessourceF(type)

    private fun <T : JsonRessource> JsonResourceCompanion<T>.newNullable(obj: JsonObject?) = new(obj ?: JsonObject(emptyMap()))

    //<editor-fold desc="Extention delegated">

    operator fun <E : JsonRessource> JsonRessourceF<E>.getValue(thisRef: Any?, property: KProperty<*>): E {
        return this.type.newNullable(obj.obj(property.name))
    }

    operator fun <E : JsonRessource> JsonRessourceF<E>.setValue(thisRef: Any?, property: KProperty<*>, value: E?) {
        obj.set(property.name, value)
    }

    operator fun JsonString.getValue(thisRef: Any?, property: KProperty<*>): String {
        return obj.string(property.name) ?: this.defaultValue
    }

    operator fun JsonString.setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
        obj.set(property.name, value)
    }

    operator fun JsonInt.getValue(thisRef: Any?, property: KProperty<*>): Int {
        return obj.int(property.name) ?: this.defaultValue
    }

    operator fun JsonInt.setValue(thisRef: Any?, property: KProperty<*>, value: Int?) {
        obj.set(property.name, value)
    }

    operator fun JsonFloat.getValue(thisRef: Any?, property: KProperty<*>): Float {
        return obj.double(property.name)?.toFloat() ?: this.defaultValue
    }

    operator fun JsonFloat.setValue(thisRef: Any?, property: KProperty<*>, value: Float?) {
        obj.set(property.name, value)
    }

    operator fun JsonLong.getValue(thisRef: Any?, property: KProperty<*>): Long {
        return obj.long(property.name) ?: this.defaultValue
    }

    operator fun JsonLong.setValue(thisRef: Any?, property: KProperty<*>, value: Long?) {
        obj.set(property.name, value)
    }

    operator fun JsonDouble.getValue(thisRef: Any?, property: KProperty<*>): Double {
        return obj.double(property.name) ?: this.defaultValue
    }

    operator fun JsonDouble.setValue(thisRef: Any?, property: KProperty<*>, value: Double?) {
        obj.set(property.name, value)
    }

    operator fun JsonBoolean.getValue(thisRef: Any?, property: KProperty<*>): Boolean {
        return obj.boolean(property.name) ?: this.defaultValue
    }

    operator fun JsonBoolean.setValue(thisRef: Any?, property: KProperty<*>, value: Boolean?) {
        obj.set(property.name, value)
    }

    operator fun JsonBigInt.getValue(thisRef: Any?, property: KProperty<*>): BigInteger {
        return obj.bigInt(property.name) ?: this.defaultValue
    }

    operator fun JsonBigInt.setValue(thisRef: Any?, property: KProperty<*>, value: BigInteger?) {
        obj.set(property.name, value)
    }

    operator fun JsonDate.getValue(thisRef: Any?, property: KProperty<*>): LocalDate {
        return LocalDate.parse(obj.string(property.name), this.format)
    }

    operator fun JsonDate.setValue(thisRef: Any?, property: KProperty<*>, value: LocalDate?) {
        obj.set(property.name, value!!.format(this.format))
    }

    operator fun JsonDateTime.getValue(thisRef: Any?, property: KProperty<*>): LocalDateTime {
        return LocalDateTime.parse(obj.string(property.name), this.format)
    }

    operator fun JsonDateTime.setValue(thisRef: Any?, property: KProperty<*>, value: LocalDateTime?) {
        obj.set(property.name, value!!.format(this.format))
    }

    operator fun JsonTime.getValue(thisRef: Any?, property: KProperty<*>): LocalTime {
        return LocalTime.parse(obj.string(property.name), this.format)
    }

    operator fun JsonTime.setValue(thisRef: Any?, property: KProperty<*>, value: LocalTime?) {
        obj.set(property.name, value!!.format(this.format))
    }

    operator fun <T> JsonList<T>.getValue(thisRef: Any?, property: KProperty<*>): Collection<T> {
        return obj.array<T>(property.name) ?: this.defaultValue
    }

    operator fun <T> JsonList<T>.setValue(thisRef: Any?, property: KProperty<*>, value: Collection<T>?) {
        obj.set(property.name, value)
    }

    operator fun <E : Enum<E>> JsonEnum<E>.getValue(thisRef: Any?, property: KProperty<*>): E {
        return this[obj.string(property.name)!!]
    }

    operator fun <E : Enum<E>> JsonEnum<E>.setValue(thisRef: Any?, property: KProperty<*>, value: E) {
        obj.set(property.name, value)
    }
    //</editor-fold>

}

open class SubJsonResource(obj: JsonObject) : JsonRessource(obj) {
}


class JsonString(val defaultValue: String)
class JsonEnum<E : Enum<E>>(type: KClass<E>) {
    private val maps = HashMap<String, E>(type.java.enumConstants.size)

    init {
        for (v in type.java.enumConstants) {
            maps.put(v.toString().toUpperCase(), v)
        }
    }

    operator fun get(key: String): E {
        return maps[key.toUpperCase()]!!
    }
}

class JsonInt(val defaultValue: Int)
class JsonFloat(val defaultValue: Float)
class JsonLong(val defaultValue: Long)
class JsonDouble(val defaultValue: Double)
class JsonBoolean(val defaultValue: Boolean)
class JsonDate(val format: DateTimeFormatter)
class JsonDateTime(val format: DateTimeFormatter)
class JsonTime(val format: DateTimeFormatter)
class JsonBigInt(val defaultValue: BigInteger)

class JsonList<T>(val defaultValue: Collection<T> = emptySet())
class JsonRessourceF<T : JsonRessource>(val type: JsonResourceCompanion<T>)