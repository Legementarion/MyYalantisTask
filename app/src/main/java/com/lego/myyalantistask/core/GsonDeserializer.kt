package com.lego.myyalantistask.core

import com.google.gson.*
import java.lang.reflect.Type
import com.google.gson.Gson

class GsonDeserializer<T> : JsonDeserializer<T> {

    @Throws(JsonParseException::class)
    override fun deserialize(je: JsonElement, type: Type, jdc: JsonDeserializationContext): T {
        val mappings = JsonObject()
        val data = je.asJsonObject.get("data")
        val children = data.asJsonObject.get("children")
        val newData = JsonArray()
        children.asJsonArray.forEach { newData.add(it.asJsonObject.get("data")) }
        mappings.add("data", newData)

        return Gson().fromJson(mappings, type)
    }
}