package com.shuyu.github.kotlin.module.image

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.github.chrisbanes.photoview.OnViewTapListener
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.OnItemClickListener
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.gsyimageloader.GSYImageLoaderManager
import com.shuyu.github.kotlin.common.gsyimageloader.GSYLoadOption
import com.shuyu.github.kotlin.common.utils.FileUtils
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.ui.adapter.TextListAdapter
import kotlinx.android.synthetic.main.activity_image_preview.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions


/**
 * Created by guoshuyu
 * Date: 2018-11-15
 */
@RuntimePermissions
@Route(path = ARouterAddress.ImagePreViewActivity)
class ImagePreViewActivity : AppCompatActivity(), OnViewTapListener, View.OnLongClickListener, OnItemClickListener, ARouterInjectable {

    @Autowired
    @JvmField
    var url = ""

    companion object {
        fun gotoImagePreView(url: String) {
            getRouterNavigation(ARouterAddress.ImagePreViewActivity, url).navigation()
        }

        fun getRouterNavigation(uri: String, url: String): Postcard {
            return ARouter.getInstance()
                    .build(uri)
                    .withString("url", url)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_preview)

        preview_photo_view.setOnViewTapListener(this)
        preview_photo_view.setOnLongClickListener(this)


        val option = GSYLoadOption()
                .setDefaultImg(R.drawable.epmty_img)
                .setErrorImg(R.drawable.epmty_img)
                .setCircle(false)
                .setUri(url)
        GSYImageLoaderManager.sInstance.imageLoader().loadImage(option, preview_photo_view, null)
    }

    override fun onViewTap(view: View?, x: Float, y: Float) {
        finish()
    }

    override fun onLongClick(v: View?): Boolean {
        val dialog = DialogPlus.newDialog(this)
                .setAdapter(TextListAdapter(this, arrayListOf(getString(R.string.saveToLocal), getString(R.string.cancel))))
                .setOnItemClickListener(this)
                .setExpanded(false)
                .create()
        dialog.show()
        return true
    }

    override fun onItemClick(dialog: DialogPlus?, item: Any?, view: View?, position: Int) {
        when (position) {
            0 -> {
                saveImageWithPermissionCheck()
            }
            1 -> {
            }
        }
        dialog?.dismiss()
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun saveImage() {
        FileUtils.download(applicationContext, url, "gsy")
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun showDeniedForSaveImage() {
        Toast.makeText(this, R.string.permission_storage_denied, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }
}