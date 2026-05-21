# GSYGithubAppKotlin 项目协作规约

供 AI Agent / 协作开发者执行任务时遵循。本文件随项目演进持续维护。

---

## 1. 发布流程（强制）

> **核心原则：R8 编译通过 ≠ 运行时安全。任何 release tag 推送之前，必须装机回归核心路径。**

### 1.1 发版前置条件

发起一次正式版本发布（推送 `vX.Y.Z` tag）之前，**必须**全部满足：

1. **版本号已升级**：在 [app/build.gradle](file:///d:/workspace/project/GSYGithubAppKotlin/app/build.gradle#L20-L21) 同步升级 `versionCode` 和 `versionName`，不允许只改其中一个。
2. **CI 编译通过**：`master` 上最近一次 push 的 CI / Release workflow 已绿（含 R8 minify、shrinkResources、签名）。
3. **Release APK 已装机回归**（见 1.2）。
4. **变更说明已就绪**：commit 信息或 release notes 清晰描述本次变更，特别是 ProGuard 规则、依赖、`targetSdk`、签名、`<queries>` 等敏感面变更。

### 1.2 装机回归 Checklist（必跑）

从 GitHub Release（或 CI artifact）拉到 release 签名版 APK 后，在真机上至少完成以下 5 项：

| # | 场景 | 验证点 |
|---|---|---|
| 1 | 冷启动 | `topResumedActivity` 落到 `MainActivity`；logcat 无 `FATAL` / `AndroidRuntime` 异常；无 `NoClassDefFoundError` / `Init provider failed` |
| 2 | 推荐 / 趋势 Tab | 仓库卡片、头像渲染正常（验证 Retrofit + OkHttp + Glide 在 minify 下工作） |
| 3 | 仓库详情页 | ARouter 跳转可达，4 tab（详情/动态/文件/Issues）切换不崩；fork/watch/star 按钮可响应 |
| 4 | "我的"页面 | 用户信息 + 贡献热力图渲染（验证 SimpleXML / HTML 解析路径） |
| 5 | 侧边栏「版本更新」→ 浏览器跳转 | 能正确拉起浏览器到 [releases/latest](https://github.com/CarGuo/GSYGithubAppKotlin/releases/latest)（验证 [AndroidManifest.xml](file:///d:/workspace/project/GSYGithubAppKotlin/app/src/main/AndroidManifest.xml#L13-L24) 的 `<queries>` 在 `targetSdk` 36 下生效 + [browse()](file:///d:/workspace/project/GSYGithubAppKotlin/app/src/main/java/com/shuyu/github/kotlin/common/compat/AnkoCompat.kt#L25-L45) 健壮性） |

只要任一项失败，**禁止打 tag**；先修代码 / 调 ProGuard，重新过 CI 后回到本流程。

### 1.3 装机回归操作流程（命令模板）

```powershell
# 1. 拉 Release APK（或从 workflow artifact）
$env:GH_TOKEN = "<PAT>"
gh release download vX.Y.Z -R CarGuo/GSYGithubAppKotlin -p "app-release.apk" -D build/release-test --clobber

# 2. 装机
adb uninstall com.shuyu.github.kotlin
adb install -r build/release-test/app-release.apk

# 3. 启动 + 抓 FATAL
adb logcat -c
adb shell am start -n com.shuyu.github.kotlin/.module.StartActivity
Start-Sleep -Seconds 6
adb logcat -d *:E | Select-String -Pattern "AndroidRuntime|FATAL|Caused by|com\.shuyu\.github"

# 4. 确认前台 Activity
adb shell dumpsys activity activities | Select-String "topResumedActivity"

# 5. 截图回归（核心 5 个页面）
adb exec-out screencap -p > build/release-test/screen-XX.png
```

### 1.4 Tag 推送规则

- 一次 tag 对应一个**已经装机验证过的**构建。
- **禁止** force-update 已发布 release 对应的 tag（`git push -f origin vX.Y.Z`）。如果发现已发布的 tag 对应 APK 有问题：
  - 优先选择**新增 patch 版本**（如 `1.4.0` → `1.4.1`），而不是覆盖原 tag。
  - 原因：覆盖 tag 不会自动重新触发 GitHub Release Action 的 "Create Release" 步骤（已存在会 422），还会让用户已下载的旧 APK 与 tag 不一致，调试困难。
- Tag 命名：`vMAJOR.MINOR.PATCH`（带 `v` 前缀，与 [.github/workflows/release.yml](file:///d:/workspace/project/GSYGithubAppKotlin/.github/workflows/release.yml) 的触发规则一致）。

---

## 2. ProGuard / R8 规约

> 本项目 release 构建启用 `minifyEnabled true` + `shrinkResources true`。AGP 9 自带 R8 9.0+ 默认 **full mode**，比 legacy mode 更激进。

### 2.1 反射目标必须显式 keep

R8 full mode **不做 transitive 父类查找**，以下写法在本项目曾导致运行时崩溃：

```proguard
# ❌ 不可靠：自定义 Fragment 多隔一层基类时推断不到
-keep class com.shuyu.github.kotlin.module.** extends androidx.fragment.app.Fragment { *; }
```

正确做法（见 [app/proguard-rules.pro](file:///d:/workspace/project/GSYGithubAppKotlin/app/proguard-rules.pro#L449-L458)）：

```proguard
# ✅ 全量 keep 业务模块（ARouter Class.forName 反射目标）
-keep class com.shuyu.github.kotlin.module.** { *; }
-keepnames class com.shuyu.github.kotlin.module.**
-keep @com.alibaba.android.arouter.facade.annotation.Route class * { *; }
```

### 2.2 `-dontwarn` 仅消除编译警告，不保护运行时

历史教训：v1.4.0 仅靠 `-dontwarn javax.lang.model.**` / `-dontwarn javax.xml.stream.**` 通过了 R8 编译，但 Application `onCreate` 里 `Postcard.navigation` 立刻崩 `androidx.fragment.app.i0: Init provider failed!`。

**任何新增 `-dontwarn` 都必须配套装机验证**，确认对应的反射 / SPI 调用路径在运行时不会被混淆裁剪。

### 2.3 新增依赖的 ProGuard 自检流程

引入新依赖（特别是带反射 / 注解处理 / SPI 的：ARouter、Retrofit、SimpleXML、Room、Glide、RxJava、kotlinx.serialization）后：

1. 优先看依赖自带的 `consumer-rules.pro`（一般已合规）。
2. 跑一次 `:app:assembleRelease`，注意 R8 的 `Missing classes` 报告。
3. 装机跑核心路径（参考 1.2）。

---

## 3. 浏览器 / Intent 跳转规约（targetSdk 30+）

Android 11 起 package visibility 收紧，必须在 [AndroidManifest.xml](file:///d:/workspace/project/GSYGithubAppKotlin/app/src/main/AndroidManifest.xml#L13-L24) 显式声明 `<queries>` 才能 resolve 到外部应用。

- 任何 `Intent.ACTION_VIEW` 跳转 `http(s)` 的代码必须：
  1. Manifest 中已声明 `<queries>` 含对应 scheme + `CATEGORY_BROWSABLE`。
  2. 调用前 `intent.resolveActivity(packageManager) != null` 预检。
  3. 异常路径有用户感知（toast / 提示），禁止静默 catch。

参考实现：[AnkoCompat.browse()](file:///d:/workspace/project/GSYGithubAppKotlin/app/src/main/java/com/shuyu/github/kotlin/common/compat/AnkoCompat.kt#L25-L45)。

---

## 4. 工作环境注意

- 本机（Windows）当前 JDK 17，**不能本地跑** `:app:assembleRelease`：
  - dataBinding parser 要 JDK 24（class file 65.0），JDK 17 只到 61.0。
  - 国内网络拉 `gradlePluginPortal` 偶发 SSL 握手失败。
- **release 构建一律走 CI 验证**。本地只跑 `:app:assembleDebug` 和单元测试。
- 调试 release 流程时，提交后用 `gh run list` / `gh run view --log-failed` 拉远程日志，不要在本机硬复现。

---

## 5. 代码风格

- 不主动添加注释（除非用户要求）。
- 不主动新建 `*.md` / README（除非用户要求）。
- 修改文件前先 `Read` 确认最新内容，避免基于旧上下文编辑。
- 引用文件 / 函数 / 行号统一使用绝对路径 markdown 链接：`[name](file:///abs/path#Lstart-Lend)`。

---

## 6. 历史 Pitfall 索引

| 版本 | 问题 | 修复 |
|---|---|---|
| v1.4.0 | R8 minify 后 ARouter `Class.forName` 反射目标被裁，首屏 `Init provider failed!` | proguard 改用 `-keep class com.shuyu.github.kotlin.module.** { *; }`，新发 v1.4.1 |
| v1.4.0 | R8 编译报 Missing class `javax.lang.model.element.Element`（ARouter）| `-dontwarn javax.lang.model.**` |
| v1.4.0 | R8 编译报 Missing class `javax.xml.stream.**`（SimpleXML）| `-dontwarn javax.xml.stream.**` |
| v1.4.0 | 「检查更新」跳 `releases` 列表而非 `releases/latest` | [MainDrawerController.RELEASE_PAGE_URL](file:///d:/workspace/project/GSYGithubAppKotlin/app/src/main/java/com/shuyu/github/kotlin/module/main/MainDrawerController.kt#L52-L54) 改为 `releases/latest` |
| v1.4.0 | targetSdk 36 下 `Intent.ACTION_VIEW` 无浏览器可解析 | Manifest 加 `<queries>` + `browse()` 加 `resolveActivity` 预检 |
