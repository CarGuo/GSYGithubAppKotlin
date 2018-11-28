// ILocalMessage.aidl
package com.shuyu.github.kotlin;

// Declare any non-default types here with import statements+

import com.shuyu.github.kotlin.ILocalMessageCallBack;

interface ILocalMessage {

    void sendMessage(in String message);

    int getVersion();

	void registerCallBack(in ILocalMessageCallBack callback);
}
