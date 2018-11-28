// ILocalMessage.aidl
package com.shuyu.github.kotlin;

// Declare any non-default types here with import statements

interface ILocalMessage {

    void sendMessage(in String message);

    int getVersion();
}
