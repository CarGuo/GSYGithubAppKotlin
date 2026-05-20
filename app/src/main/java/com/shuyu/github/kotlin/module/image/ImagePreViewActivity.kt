package com.shuyu.github.kotlin.module.image

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
import com.shuyu.github.kotlin.databinding.ActivityImagePreviewBinding
import com.shuyu.github.kotlin.di.ARouterInjectable
import com.shuyu.github.kotlin.module.ARouterAddress
import com.shuyu.github.kotlin.ui.adapter.TextListAdapter


/**
 * Created by guoshuyu
 * Date: 2018-11-15
 */
@Route(path = ARouterAddress.ImagePreViewActivity)
class ImagePreViewActivity : AppCompatActivity(), OnViewTapListener, View.OnLongClickListener,
    OnItemClickListener, ARouterInjectable {

    @Autowired
    @JvmField
    var url = ""

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    companion object {
        fun gotoImagePreView(url: String) {
            getRouterNavigation(ARouterAddress.ImagePreViewActivity, url).navigation()
        }

        fun getRouterNavigation(uri: String, url: String): Postcard {
            return ARouter.getInstance().build(uri).withString("url", url)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vb = ActivityImagePreviewBinding.inflate(layoutInflater)
        setContentView(vb.root)
        vb.previewPhotoView.setOnViewTapListener(this)
        vb.previewPhotoView.setOnLongClickListener(this)

        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) {
                saveImage()
            } else {
                Toast.makeText(
                    this,
                    R.string.permission_storage_denied,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val option =
            GSYLoadOption().setDefaultImg(R.drawable.epmty_img).setErrorImg(R.drawable.epmty_img)
                .setCircle(false).setUri(url)
        GSYImageLoaderManager.sInstance.imageLoader().loadImage(option, vb.previewPhotoView, null)
    }

    override fun onViewTap(view: View?, x: Float, y: Float) {
        finish()
    }

    override fun onLongClick(v: View?): Boolean {
        val dialog = DialogPlus.newDialog(this).setAdapter(
                TextListAdapter(
                    this, arrayListOf(getString(R.string.saveToLocal), getString(R.string.cancel))
                )
            ).setOnItemClickListener(this).setExpanded(false).create()
        dialog.show()
        return true
    }

    override fun onItemClick(dialog: DialogPlus?, item: Any?, view: View?, position: Int) {
        when (position) {
            0 -> {
                requestSaveImage()
            }

            1 -> {
            }
        }
        dialog?.dismiss()
    }

    private fun requestSaveImage() {
        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(this, permission)
            == PackageManager.PERMISSION_GRANTED
        ) {
            saveImage()
        } else {
            requestPermissionLauncher.launch(permission)
        }
    }

    fun saveImage() {
        FileUtils.download(applicationContext, url, "gsy")
    }
}
