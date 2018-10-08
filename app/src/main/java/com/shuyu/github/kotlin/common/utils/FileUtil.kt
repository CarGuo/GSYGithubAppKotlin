package com.shuyu.github.kotlin.common.utils

import android.content.Context
import android.os.Environment
import android.util.Base64

import java.io.File
import java.io.FileInputStream

/**
 * 文件管理
 */
object FileUtil {

    private const val IMAGE_CACHE_DIR_NAME = "image_cache"

    private const val HTTP_CACHE_DIR_NAME = "http_cache"

    /**
     * 检查内部存储是否可用
     * @return
     */
    val isExternalStorageEnable: Boolean
        get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    /**
     * 获取缓存文件夹
     *
     * @param context 上下文
     * @param dirName 文件夹名称
     * @return 缓存文件夹
     */
    fun getCacheDir(context: Context, dirName: String): File {
        val rootDir = context.externalCacheDir
        val cacheFile = File(rootDir, dirName)
        if (!cacheFile.exists()) {
            cacheFile.mkdir()
        }
        return cacheFile
    }


    /**
     * 获取图片缓存文件夹
     *
     * @param context 上下文
     * @return 图片缓存文件夹
     */
    fun getImageCacheDir(context: Context): File {
        return getCacheDir(context, IMAGE_CACHE_DIR_NAME)
    }

    /**
     * 获取网络请求缓存文件夹
     * @param context 上下文
     * @return 网络请求缓存文件夹
     */
    fun getHttpImageCacheDir(context: Context): File {
        return getCacheDir(context, HTTP_CACHE_DIR_NAME)
    }

    /**
     * 将文件转化为字节数组字符串，并对其进行Base64编码处理
     * @return
     */
    @Throws(Exception::class)
    fun encodeBase64File(path: String): String {
        val file = File(path)
        val inputFile = FileInputStream(file)
        val buffer = ByteArray(file.length().toInt())
        inputFile.read(buffer)
        inputFile.close()
        return Base64.encodeToString(buffer, Base64.DEFAULT)
    }

}
