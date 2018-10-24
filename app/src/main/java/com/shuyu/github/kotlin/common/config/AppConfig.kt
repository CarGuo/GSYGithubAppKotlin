package com.shuyu.github.kotlin.common.config

/**
 * App 配置
 */
object AppConfig {

    const val GITHUB_BASE_URL = "https://github.com/"

    const val GITHUB_API_BASE_URL = "https://api.github.com/"

    const val GITHUB_CONTENT_BASE_URL = "https://raw.githubusercontent.com/"

    const val GRAPHIC_HOST = "https://ghchart.rshah.org/"

    const val PAGE_SIZE = 30

    const val HTTP_TIME_OUT = 20 * 1000L

    const val HTTP_MAX_CACHE_SIZE = 16 * 1024 * 1024L

    const val IMAGE_MAX_CACHE_SIZE = 16 * 1024 * 1024L

    const val CACHE_MAX_AGE = 7 * 24 * 60 * 60L

    const val ACCESS_TOKEN = "accessToken"

    const val USER_BASIC_CODE = "userBasicCode"

    const val USER_NAME = "user_name"

    const val PASSWORD = "password"

    const val USER_INFO = "userInfo"


}