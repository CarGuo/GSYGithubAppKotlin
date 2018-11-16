package com.shuyu.github.kotlin.common.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Base64
import com.bumptech.glide.Glide
import com.shuyu.github.kotlin.R
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast
import java.io.*
import kotlin.experimental.and

/**
 * Created by guoshuyu
 * Date: 2018-11-16
 */

object FileUtils {


    fun getLocalPath(): String {
        val dir = Environment.getExternalStorageDirectory().absolutePath + "/gsygithub/"
        val file = File(dir)
        if (!file.exists()) {
            file.mkdirs()
        }
        return dir
    }

    fun download(context: Context, url: String, picName: String) {

        doAsync {
            val file: File?
            try {
                val future = Glide
                        .with(context)
                        .downloadOnly()
                        .load(url)
                file = future.submit().get()

                val path = getLocalPath()

                val type = getImageTypeByStream(file)

                val fileName = picName + "_" + System.currentTimeMillis().toString() + "." + type

                val destFile = File(path, fileName)

                copyFile(file, destFile)

                // 最后通知图库更新
                context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.fromFile(File(destFile.path))))

                context.runOnUiThread {
                    context.toast(context.getString(R.string.saveSuccess) + destFile.absolutePath)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun copyFile(sourceFile: File?, desFile: File): Boolean {
        var res = false
        if (null != sourceFile && sourceFile.exists()) {
            var inStream: InputStream? = null
            var fos: FileOutputStream? = null

            try {
                inStream = FileInputStream(sourceFile)
                fos = FileOutputStream(desFile)
                val buffer = ByteArray(1024)
                var byteRead = 0
                while (byteRead != -1) {
                    byteRead = inStream.read(buffer)
                    fos.write(buffer, 0, byteRead)
                }
                fos.flush()
                res = true
            } catch (var15: Exception) {
                res = false
                var15.printStackTrace()
            } finally {
                try {
                    fos?.close()

                    inStream?.close()
                } catch (var14: IOException) {
                    var14.printStackTrace()
                }

            }
        }

        return res
    }

    fun getImageTypeByStream(file: File): String {
        var inStream: InputStream? = null
        try {
            inStream = FileInputStream(file)
            return getImageTypeByStream(inStream)
        } catch (var15: Exception) {
            var15.printStackTrace()
        } finally {
            try {
                inStream?.close()
            } catch (var14: IOException) {
                var14.printStackTrace()
            }
        }
        return "jpg"
    }

    fun getImageTypeByStream(input: FileInputStream): String {
        val b = ByteArray(4)

        try {
            input.read(b, 0, b.size)
        } catch (var3: IOException) {
            var3.printStackTrace()
        }
        val type = bytesToHexString(b)?.toUpperCase() ?: return "jpg"
        return if (type.contains("FFD8FF")) {
            "jpg"
        } else if (type.contains("89504E47")) {
            "png"
        } else if (type.contains("47494638")) {
            "gif"
        } else if (type.contains("49492A00")) {
            "tif"
        } else {
            if (type.contains("424D")) "bmp" else type
        }
    }

    fun bytesToHexString(src: ByteArray?): String? {
        if (src != null && src.isNotEmpty()) {
            val stringBuilder = StringBuilder(0)

            for (i in src.indices) {
                val v = src[i] and 255.toByte()
                val hv = Integer.toHexString(v.toInt())
                if (hv.length < 2) {
                    stringBuilder.append(0)
                }

                stringBuilder.append(hv)
            }

            return stringBuilder.toString()
        } else {
            return null
        }
    }


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