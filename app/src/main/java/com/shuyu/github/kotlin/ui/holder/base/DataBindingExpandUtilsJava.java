package com.shuyu.github.kotlin.ui.holder.base;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.mikepenz.iconics.view.IconicsImageView;
import com.shuyu.github.kotlin.ui.view.GSYWebViewContainer;

public final class DataBindingExpandUtilsJava {

    private DataBindingExpandUtilsJava() {
    }

    @BindingAdapter("image_blur")
    public static void loadImageBlur(ImageView view, String url) {
        DataBindingExpandUtils.loadImageBlur(view, url);
    }

    @BindingAdapter(value = {"userHeaderUrl", "userHeaderSize"}, requireAll = false)
    public static void loadImage(ImageView view, String url, int size) {
        DataBindingExpandUtils.loadImage(view, url, size);
    }

    @BindingAdapter("webViewUrl")
    public static void webViewUrl(GSYWebViewContainer view, String url) {
        DataBindingExpandUtils.webViewUrl(view, url);
    }

    @BindingAdapter(value = {"markdownText", "style"}, requireAll = false)
    public static void markdownText(TextView view, String text, String style) {
        DataBindingExpandUtils.markdownText(view, text, style);
    }

    @BindingAdapter("keyListener")
    public static void editTextKeyListener(EditText view, View.OnKeyListener listener) {
        DataBindingExpandUtils.editTextKeyListener(view, listener);
    }

    @BindingAdapter(value = {"iiv_icon", "iiv_color"}, requireAll = false)
    public static void iconicsImage(IconicsImageView view, String value, Integer colorId) {
        DataBindingExpandUtils.editTextKeyListener(view, value, colorId);
    }
}
