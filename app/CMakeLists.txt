#需求的Cmake最小版本号
cmake_minimum_required(VERSION 3.4.1)

#设置变量
#set(distribution_DIR ${CMAKE_SOURCE_DIR}/../distribution)
set(pathToDlib ${CMAKE_SOURCE_DIR}/src/main/)

#包含别的CmakeLists.txt
#include(${pathToDlib}/dlib/cmake)

#编译成library，参数是库的名字、库的类型、源码s(自己写的JNI文件，可以有多个)
add_library( # Sets the name of the library.
        # 生成的so库名称,并不需要和c/cpp文件名相同
        # 这里生产的so库名称将为libnative-gsy.so
        native-gsy

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        # 资源文件的路径，可以是多个资源文件
        src/main/cpp/native-test.cpp )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
        # 设置依赖库的名字，下面链接库的时候会用到
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        # 查找log依赖库
        # {sdk-path}/ndk-bundle/sysroot/usr/include/android/log.h
        log )


#设置输出库文件的位置
#set_target_properties(native-gsy PROPERTIES
#                      LIBRARY_OUTPUT_DIRECTORY
#                      ${distribution_DIR}/libs/${ANDROID_ABI})


#库需要链接（依赖）的其他的库，一起打包成第一个参数的名字下面都是链接的库。
#target_link_libraries(native-my
#                      dlib::dlib
#                      log)


target_link_libraries( # Specifies the target library.
        # 目标库
        native-gsy

        # Links the target library to the log library
        # included in the NDK.
        # 依赖库，可以是多个
        ${log-lib} )


SET(CMAKE_BUILE_TYPE "RELEASE")
SET(CMAKE_CXX_FLAGS_RELEASE "$ENV{CXXFLAGS} -O3 -Wall")
SET(CMAKE_BUILE_TYPE "DEBUG")
SET(CMAKE_CXX_FLAGS_DEBUG "-g -Wall")