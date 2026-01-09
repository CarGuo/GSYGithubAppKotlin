# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep the Strikethrough class from being removed by R8
-keep class org.commonmark.ext.gfm.strikethrough.Strikethrough { *; }

# ============ Retrofit & OkHttp ProGuard Rules ============

# Keep generic signature for Retrofit (required for Observable<Response<T>> to work)
-keepattributes Signature

# Keep Exceptions for Retrofit
-keepattributes Exceptions

# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature.
-keepattributes InnerClasses

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Keep annotation default values (e.g., retrofit2.http.Field.encoded).
-keepattributes AnnotationDefault

# Keep EnclosingMethod attribute for proper generic signature resolution
-keepattributes EnclosingMethod

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and). Since we do keep all Retrofit interfaces, we can just ignore this warning.
-dontwarn retrofit2.Call

# Keep Retrofit service interfaces (crucial for Observable<Response<T>> handling)
-keep,allowobfuscation interface * extends retrofit2.Call

# Keep all Retrofit service interfaces in this project (preserve full method signatures for generics)
# Do NOT allow obfuscation or shrinking - generic type info must be preserved
-keep interface com.shuyu.github.kotlin.service.** { *; }

# Keep all Retrofit HTTP annotations (CRITICAL for proper URL building)
-keep class retrofit2.http.** { *; }

# Keep Retrofit2 Response class with its generic signature (CRITICAL for RxJava2 adapter)
-keep class retrofit2.Response { *; }

# Keep Retrofit2 internal call adapter classes needed for Observable<Response<T>>
-keep class retrofit2.adapter.rxjava2.** { *; }

# Keep Retrofit service method return types (prevents stripping generic signatures)
-keep class retrofit2.CallAdapter { *; }
-keep class retrofit2.CallAdapter$Factory { *; }
-keep class retrofit2.Converter { *; }
-keep class retrofit2.Converter$Factory { *; }

# Keep Retrofit itself
-keep class retrofit2.Retrofit { *; }
-keep class retrofit2.Retrofit$Builder { *; }

# Keep service interface method parameters names (for @Path, @Query, etc.)
-keepclasseswithmembers interface com.shuyu.github.kotlin.service.** {
    @retrofit2.http.* <methods>;
}

# ============ RxJava2 ProGuard Rules ============

-dontwarn io.reactivex.**
-keep class io.reactivex.** { *; }
-keepclassmembers class io.reactivex.** { *; }

# ============ OkHttp ProGuard Rules ============

# JSR 330 not required
-dontwarn javax.inject.**

# OkHttp platform used only on JVM and when Conscrypt and other security providers are available.
-dontwarn okhttp3.internal.platform.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# Keep OkHttp classes for proper network operations
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

# Keep OkHttp logging interceptor
-keep class okhttp3.logging.** { *; }

# ============ Gson ProGuard Rules ============

# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**

# Keep data model classes for Gson serialization
-keep class com.shuyu.github.kotlin.model.** { *; }

# ============ Kotlin ProGuard Rules ============

-dontwarn kotlin.**
-keep class kotlin.Metadata { *; }
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}

# Keep Kotlin property delegation (used in GSYPreference)
-keepclassmembers class * {
    *** getValue(java.lang.Object, kotlin.reflect.KProperty);
    *** setValue(java.lang.Object, kotlin.reflect.KProperty, ***);
}

# Keep Kotlin reflect classes
-keep class kotlin.reflect.** { *; }
-dontwarn kotlin.reflect.**

# ============ Project-specific Classes ProGuard Rules ============

# Keep network interceptors (they use OkHttp interfaces)
-keep class com.shuyu.github.kotlin.common.net.** { *; }

# Keep utility classes that use reflection
-keep class com.shuyu.github.kotlin.common.utils.** { *; }

# Keep config classes (constants used for SharedPreferences keys)
-keep class com.shuyu.github.kotlin.common.config.** { *; }

# Keep DataBindingComponent implementation
-keep class com.shuyu.github.kotlin.ui.holder.base.GSYDataBindingComponent { *; }
-keep class com.shuyu.github.kotlin.ui.holder.base.DataBindingExpandUtils { *; }
-keep class com.shuyu.github.kotlin.ui.holder.base.DataBindingExpandUtils$Companion { *; }

# Keep Repository classes (they use Retrofit services)
-keep class com.shuyu.github.kotlin.repository.** { *; }

# Keep DAO classes (database access objects)
-keep class com.shuyu.github.kotlin.repository.dao.** { *; }

# Keep Conversion classes (model conversion logic)
-keep class com.shuyu.github.kotlin.model.conversion.** { *; }

# ============ Dagger ProGuard Rules ============

-dontwarn com.google.errorprone.annotations.**

# Keep Dagger generated classes
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.internal.Factory { *; }

# Keep Dagger Android support
-keep class dagger.android.** { *; }
-dontwarn dagger.android.**

# Keep project DI classes
-keep class com.shuyu.github.kotlin.di.** { *; }

# Keep all Dagger-generated components and modules
-keep class **_Factory { *; }
-keep class **_MembersInjector { *; }
-keep class *$$InjectAdapter { *; }
-keep class *$$ModuleAdapter { *; }

# Keep classes with @Inject constructors
-keepclasseswithmembernames class * {
    @javax.inject.Inject <init>(...);
}

# Keep @Inject fields
-keepclassmembers class * {
    @javax.inject.Inject <fields>;
}

# ============ ARouter ProGuard Rules ============

-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
# If using byType argument injection, keep the classes injected
-keep class * implements com.alibaba.android.arouter.facade.template.IProvider

# ============ Realm ProGuard Rules ============

-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class *
-dontwarn io.realm.**

# Keep Realm model classes
-keep class com.shuyu.github.kotlin.common.db.** { *; }

# ============ Glide ProGuard Rules ============

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}

# ============ EventBus ProGuard Rules ============

-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# If using @Subscriber method annotations
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

# ============ Markwon ProGuard Rules ============

-keep class io.noties.markwon.** { *; }
-dontwarn io.noties.markwon.**

# Keep Prism4j languages
-keep class io.noties.prism4j.** { *; }
-dontwarn io.noties.prism4j.**

# CommonMark
-keep class org.commonmark.** { *; }
-dontwarn org.commonmark.**

# ============ SimpleXML ProGuard Rules ============

-keep public class org.simpleframework.** { *; }
-keep class org.simpleframework.xml.** { *; }
-keep class org.simpleframework.xml.core.** { *; }
-keep class org.simpleframework.xml.util.** { *; }
-keepattributes ElementList, Root
-keepclassmembers class * {
    @org.simpleframework.xml.* *;
}

# ============ Iconics ProGuard Rules ============

-keep class *.R
-keep class **.R$* {
    <fields>;
}
-keep class com.mikepenz.iconics.** { *; }
-keep class com.mikepenz.iconics.typeface.IIcon { *; }
-keep class * implements com.mikepenz.iconics.typeface.ITypeface { *; }

# ============ AgentWeb ProGuard Rules ============

-keep class com.just.agentweb.** { *; }
-dontwarn com.just.agentweb.**

# ============ MaterialDrawer ProGuard Rules ============

-keep class com.mikepenz.materialdrawer.** { *; }

# ============ Lottie ProGuard Rules ============

-dontwarn com.airbnb.lottie.**
-keep class com.airbnb.lottie.** { *; }

# ============ Bugly ProGuard Rules ============

-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

# ============ PhotoView ProGuard Rules ============

-keep class com.github.chrisbanes.photoview.** { *; }

# ============ PermissionsDispatcher ProGuard Rules ============

-keep class permissions.dispatcher.** { *; }
-keep interface permissions.dispatcher.** { *; }
-keep class **PermissionsDispatcher { *; }
-keepclasseswithmembernames class * {
    @permissions.dispatcher.* <methods>;
}

# ============ WebView JavaScript Interface ============

# Keep JavaScript interface classes
-keepclassmembers class com.shuyu.github.kotlin.ui.view.GSYWebViewContainer$JsCallback {
    public *;
}
-keepattributes JavascriptInterface
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# ============ DataBinding ProGuard Rules ============

-keep class androidx.databinding.** { *; }
-dontwarn androidx.databinding.**

# ============ Navigation ProGuard Rules ============

-keep class androidx.navigation.** { *; }

# ============ Anko ProGuard Rules ============

-dontwarn org.jetbrains.anko.**

# ============ DialogPlus ProGuard Rules ============

-keep class com.orhanobut.dialogplus.** { *; }

# ============ SpinKit ProGuard Rules ============

-keep class com.github.ybq.android.spinkit.** { *; }

# ============ TextFieldBoxes ProGuard Rules ============

-keep class studio.carbonylgroup.textfieldboxes.** { *; }

# ============ DropDownMenu ProGuard Rules ============

-keep class com.yyydjk.library.** { *; }

# ============ NavigationTabBar ProGuard Rules ============

-keep class devlight.io.library.ntb.** { *; }

# ============ MarkdownView ProGuard Rules ============

-keep class com.github.tiagohm.markdownview.** { *; }
-keep class br.tiagohm.markdownview.** { *; }

# ============ CommonRecycler (LazyRecyclerAdapter) ProGuard Rules ============

-keep class com.shuyu.commonrecycler.** { *; }
-dontwarn com.shuyu.commonrecycler.**

# Keep all ViewHolder classes and their constructors (used by reflection in CommonRecycler)
-keep class com.shuyu.github.kotlin.ui.holder.** { *; }
-keep class com.shuyu.github.kotlin.ui.holder.base.** { *; }

# Keep all classes extending BindRecyclerBaseHolder with their constructors
-keep class * extends com.shuyu.commonrecycler.BindRecyclerBaseHolder {
    <init>(...);
}

# Keep DataBindingHolder subclasses constructors specifically
-keepclassmembers class * extends com.shuyu.github.kotlin.ui.holder.base.DataBindingHolder {
    <init>(android.content.Context, android.view.View, androidx.databinding.ViewDataBinding);
}

# ============ Room Database ProGuard Rules ============

-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# ============ AndroidX Startup ProGuard Rules ============

-keep class * extends androidx.startup.Initializer { *; }
-keepnames class * extends androidx.startup.Initializer

# ============ ConstraintLayout ProGuard Rules ============

-keep class androidx.constraintlayout.** { *; }
-dontwarn androidx.constraintlayout.**

# ============ Lifecycle ProGuard Rules ============

-keep class * implements androidx.lifecycle.LifecycleObserver {
    <init>(...);
}
-keep class * implements androidx.lifecycle.GeneratedAdapter {
    <init>(...);
}
-keepclassmembers class * implements androidx.lifecycle.LifecycleObserver {
    @androidx.lifecycle.OnLifecycleEvent <methods>;
}

# ============ ViewModel ProGuard Rules ============

-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}
-keepclassmembers class * extends androidx.lifecycle.AndroidViewModel {
    <init>(android.app.Application);
}

# Keep ViewModelFactory (uses reflection with Map of ViewModel providers)
-keep class com.shuyu.github.kotlin.GSYViewModelFactory { *; }

# Keep all ViewModel classes in the project
-keep class com.shuyu.github.kotlin.module.**ViewModel { *; }
-keep class com.shuyu.github.kotlin.module.**ViewModel$* { *; }

# ============ Additional Gson ProGuard Rules ============

# Prevent R8 from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent proguard from stripping interface information from TypeAdapterFactory,
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# Keep Gson itself
-keep class com.google.gson.** { *; }
-keepattributes *Annotation*

# ============ AppGlobalModel ProGuard Rules ============

# Keep AppGlobalModel (singleton used across the app)
-keep class com.shuyu.github.kotlin.model.AppGlobalModel { *; }

# ============ Keep all Activities and Fragments ============

-keep class com.shuyu.github.kotlin.module.** extends android.app.Activity { *; }
-keep class com.shuyu.github.kotlin.module.** extends androidx.fragment.app.Fragment { *; }

# ============ Kotlin Serialization (if used) ============

-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

# ============ R8 Full Mode Compatibility ============

# Keep class names for better debugging
-keepnames class com.shuyu.github.kotlin.** { *; }

# Ensure R8 doesn't remove method parameter names
-keepparameternames

# ============ Coroutines ProGuard Rules ============

-dontwarn kotlinx.coroutines.**
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# ============ Enum Classes ProGuard Rules ============

# Keep all enum classes in the project
-keep enum com.shuyu.github.kotlin.** { *; }

# Keep enum valueOf and values methods
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# ============ Sealed Classes ProGuard Rules ============

# Keep sealed class subclasses
-keep class * extends kotlin.Result { *; }

# ============ Prevent Stripping of Default Parameter Values ============

# Keep default parameter values in Kotlin functions (used in Retrofit service interfaces)
-keep class kotlin.jvm.internal.DefaultConstructorMarker { *; }

# ============ Gson TypeToken Fix for R8 ============

# Fix for Gson TypeToken in R8 full mode
-keep class * extends com.google.gson.reflect.TypeToken { *; }

