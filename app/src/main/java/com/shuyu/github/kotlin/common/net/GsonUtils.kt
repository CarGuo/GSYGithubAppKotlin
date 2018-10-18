package com.shuyu.github.kotlin.common.net

import java.util.ArrayList

import android.text.TextUtils

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonParser


object GsonUtils {

    fun getNoteJsonString(jsonString: String, note: String): String {
        if (TextUtils.isEmpty(jsonString)) {
            throw RuntimeException("getNoteJsonString jsonString empty")
        }
        if (TextUtils.isEmpty(note)) {
            throw RuntimeException("getNoteJsonString note empty")
        }
        val element = JsonParser().parse(jsonString)
        if (element.isJsonNull) {
            throw RuntimeException("getNoteJsonString element empty")
        }
        return element.asJsonObject.get(note).toString()
    }


    fun <T> parserJsonToArrayBeans(jsonString: String, note: String, beanClazz: Class<T>): List<T> {
        val noteJsonString = getNoteJsonString(jsonString, note)
        return parserJsonToArrayBeans(noteJsonString, beanClazz)
    }


    fun <T> parserJsonToArrayBeans(jsonString: String, beanClazz: Class<T>): List<T> {
        if (TextUtils.isEmpty(jsonString)) {
            throw RuntimeException("parserJsonToArrayBeans jsonString empty")
        }
        val jsonElement = JsonParser().parse(jsonString)
        if (jsonElement.isJsonNull) {
            throw RuntimeException("parserJsonToArrayBeans jsonElement empty")
        }
        if (!jsonElement.isJsonArray) {
            throw RuntimeException("parserJsonToArrayBeans jsonElement is not JsonArray")
        }
        val jsonArray = jsonElement.asJsonArray
        val beans = ArrayList<T>()
        for (jsonElement2 in jsonArray) {
            val bean = Gson().fromJson(jsonElement2, beanClazz)
            beans.add(bean)
        }
        return beans
    }


    fun <T> parserJsonToArrayBean(jsonString: String, clazzBean: Class<T>): T {
        if (TextUtils.isEmpty(jsonString)) {
            throw RuntimeException("parserJsonToArrayBean jsonString empty")
        }
        val jsonElement = JsonParser().parse(jsonString)
        if (jsonElement.isJsonNull) {
            throw RuntimeException("parserJsonToArrayBean jsonElement empty")
        }
        if (!jsonElement.isJsonObject) {
            throw RuntimeException("parserJsonToArrayBean is not object")
        }
        return Gson().fromJson(jsonElement, clazzBean)
    }


    fun <T> parserJsonToArrayBean(jsonString: String, note: String, clazzBean: Class<T>): T {
        val noteJsonString = getNoteJsonString(jsonString, note)
        return parserJsonToArrayBean(noteJsonString, clazzBean)
    }


    fun toJsonString(obj: Any?): String {
        return if (obj != null) {
            Gson().toJson(obj)
        } else {
            throw RuntimeException("obj could not be empty")
        }
    }
}

