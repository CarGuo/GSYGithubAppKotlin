//
// Created by guoshuyu on 2018/12/3.
//

#include <jni.h>
#include <string>
#include <vector>

//{sdk-path}/ndk-bundle/sysroot/usr/include/android
#include <android/log.h>

//定义输出的TAG
const char * LOG_TGA = "GSY_LOG_TAG";

//extern "C" JNIEXPORT jstring JNICALL
//Java_com_shuyu_github_kotlin_module_main_MainActivity_stringFromJNI(
//        JNIEnv* env,
//        jobject /* this */) {
//    std::string hello = "\n************* Hello from C++ *************\n";
//
//    //输出warn级别的日志信息
//    __android_log_print(ANDROID_LOG_WARN, LOG_TGA, "\n************* print Hello in jni *************\n");
//
//    return env->NewStringUTF(hello.c_str());
//}

// 1. 定义 C++ 业务逻辑函数 (名字随你起，不需要 Java_com_... 这种长名字)
jint c_add(JNIEnv *env, jobject thiz, jint a, jint b) {
    return a + b;
}

jstring c_get_string(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("Hello from Dynamic Registration!");
}

// 2. 定义映射表 (关键步骤)
// 参数1: Java 中的方法名
// 参数2: 方法签名 (可以使用 Android Studio 插件生成，或参考下表)
// 参数3: C++ 函数指针
static JNINativeMethod gMethods[] = {
        {"addNumbers", "(II)I", (void *)c_add},
        {"stringFromJNI", "()Ljava/lang/String;", (void *)c_get_string}
};

// 3. 实现 JNI_OnLoad (在 System.loadLibrary 时会自动调用)
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = nullptr;
    if (vm->GetEnv((void **)&env, JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }

    // 找到对应的 Java 类路径 (包名/类名)
    jclass clazz = env->FindClass("com/shuyu/github/kotlin/module/main/MainActivity");
    if (clazz == nullptr) {
        return JNI_ERR;
    }

    // 注册方法
    // 参数: 类, 方法数组, 方法数量
    if (env->RegisterNatives(clazz, gMethods, sizeof(gMethods) / sizeof(gMethods[0])) < 0) {
        return JNI_ERR;
    }

    return JNI_VERSION_1_6;
}