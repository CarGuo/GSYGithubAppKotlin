// ILocalMessageCallBack.aidl
package com.shuyu.github.kotlin;

import com.shuyu.github.kotlin.model.AIDLResultModel;


interface ILocalMessageCallBack {
    void sendResult(in AIDLResultModel result);
}