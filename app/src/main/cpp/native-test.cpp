//
// Created by guoshuyu on 2018/12/3.
//

#include <jni.h>
#include <string>

//{sdk-path}/ndk-bundle/sysroot/usr/include/android
#include <android/log.h>

//定义输出的TAG
const char * LOG_TGA = "GSY_LOG_TAG";

extern "C" JNIEXPORT jstring JNICALL
Java_com_shuyu_github_kotlin_module_main_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "\n************* Hello from C++ *************\n";

    //输出warn级别的日志信息
    __android_log_print(ANDROID_LOG_WARN, LOG_TGA, "\n************* print Hello in jni *************\n");

    return env->NewStringUTF(hello.c_str());
}