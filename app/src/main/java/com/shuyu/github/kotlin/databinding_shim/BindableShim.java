package com.shuyu.github.kotlin.databinding_shim;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * AGP 9 + KSP + Kotlin @Bindable 兼容补丁。
 *
 * 由于 KSP 不会把 Kotlin @Bindable 字段暴露给 dataBinding 注解处理器（dataBinding 仅扫描 javac 中的 .java
 * 源），导致 BR.java 缺少 @Bindable 字段，进而 BindingImpl.java 编译失败。
 *
 * 这里通过 Java shim 类把所有 Kotlin @Bindable 属性名以 getter 形式声明，让 dataBinding 注解处理器
 * 把这些字段名注入到 BR 类。
 */
public class BindableShim extends BaseObservable {

    @Bindable public Object getOwnerName() { return null; }
    @Bindable public Object getOwnerPic() { return null; }
    @Bindable public Object getRepositoryName() { return null; }
    @Bindable public Object getRepositoryStar() { return null; }
    @Bindable public Object getRepositoryFork() { return null; }
    @Bindable public Object getRepositoryWatch() { return null; }
    @Bindable public Object getHideWatchIcon() { return null; }
    @Bindable public Object getRepositoryType() { return null; }
    @Bindable public Object getRepositoryDes() { return null; }
    @Bindable public Object getRepositorySize() { return null; }
    @Bindable public Object getRepositoryLicense() { return null; }
    @Bindable public Object getRepositoryAction() { return null; }
    @Bindable public Object getRepositoryIssue() { return null; }

    @Bindable public Object getLogin() { return null; }
    @Bindable public Object getName() { return null; }
    @Bindable public Object getAvatarUrl() { return null; }
    @Bindable public Object getHtmlUrl() { return null; }
    @Bindable public Object getType() { return null; }
    @Bindable public Object getCompany() { return null; }
    @Bindable public Object getBlog() { return null; }
    @Bindable public Object getLocation() { return null; }
    @Bindable public Object getEmail() { return null; }
    @Bindable public Object getBio() { return null; }
    @Bindable public Object getBioDes() { return null; }
    @Bindable public Object getStarRepos() { return null; }
    @Bindable public Object getHonorRepos() { return null; }
    @Bindable public Object getPublicRepos() { return null; }
    @Bindable public Object getPublicGists() { return null; }
    @Bindable public Object getFollowers() { return null; }
    @Bindable public Object getFollowing() { return null; }
    @Bindable public Object getCreatedAt() { return null; }
    @Bindable public Object getUpdatedAt() { return null; }
    @Bindable public Object getActionUrl() { return null; }

    @Bindable public Object getUsername() { return null; }
    @Bindable public Object getImage() { return null; }
    @Bindable public Object getAction() { return null; }
    @Bindable public Object getTime() { return null; }
    @Bindable public Object getComment() { return null; }
    @Bindable public Object getContent() { return null; }
    @Bindable public Object getIssueNum() { return null; }
    @Bindable public Object getStatus() { return null; }
    @Bindable public Object getLocked() { return null; }

    @Bindable public Object getPushUserName() { return null; }
    @Bindable public Object getPushImage() { return null; }
    @Bindable public Object getPushEditCount() { return null; }
    @Bindable public Object getPushAddCount() { return null; }
    @Bindable public Object getPushReduceCount() { return null; }
    @Bindable public Object getPushTime() { return null; }
    @Bindable public Object getPushDes() { return null; }
}
