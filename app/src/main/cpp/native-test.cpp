//
// Created by guoshuyu on 2018/12/3.
//

#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_shuyu_github_kotlin_module_main_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "\n************* Hello from C++ *************\n";
    return env->NewStringUTF(hello.c_str());
}