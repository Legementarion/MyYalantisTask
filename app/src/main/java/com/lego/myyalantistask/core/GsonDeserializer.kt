package com.lego.myyalantistask.core

import com.google.gson.*
import com.lego.myyalantistask.repository.db.Result
import java.lang.reflect.Type
import com.google.gson.Gson

class GsonDeserializer: JsonDeserializer<Result> {

    @Throws(JsonParseException::class)
    override fun deserialize(je: JsonElement, type: Type, jdc: JsonDeserializationContext): Result {
        var mappings = JsonObject()
        val data = je.asJsonObject.get("data")
        val children = data.asJsonObject.get("children")
        val newData = JsonArray()
        children.asJsonArray.forEach {  newData.add(it.asJsonObject.get("data"))}
        mappings.add("data", newData)

        return Gson().fromJson(mappings, type)
    }
}