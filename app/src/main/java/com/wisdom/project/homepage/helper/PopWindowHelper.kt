package com.wisdom.project.homepage.helper


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.wisdom.ConstantString
import com.wisdom.ConstantString.REQUEST_CAMERA
import com.wisdom.project.R
import com.wisdom.project.util.ToastUtil
import java.io.File


class PopWindowHelper(private val context: Context) : View.OnClickListener {

    /**
     * 弹出选择上传方式菜单
     *
     * @param context
     */
    fun showUploadPop(context: Context) {
        val lp = (context as Activity).window.attributes
        lp.alpha = java.lang.Float.parseFloat("0.5") //0.0-1.0
        context.window.attributes = lp
        // 一个自定义的布局，作为显示的内容
        val view = LayoutInflater.from(context).inflate(
            R.layout.pop_select_upload, null
        )
        val ll_upload_img = view.findViewById<View>(R.id.ll_upload_img) as LinearLayout
        val ll_upload_camaro = view.findViewById<View>(R.id.ll_upload_camaro) as LinearLayout
        val ll_upload_file = view.findViewById<View>(R.id.ll_upload_file) as LinearLayout
        val ll_cancel = view.findViewById<View>(R.id.ll_cancel) as LinearLayout
        ll_cancel.setOnClickListener(this)
        ll_upload_file.setOnClickListener(this)
        ll_upload_camaro.setOnClickListener(this)
        ll_upload_img.setOnClickListener(this)
        window = PopupWindow(
            view,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window!!.setBackgroundDrawable(ColorDrawable(-0x1))
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window!!.isFocusable = true
        // 设置popWindow的显示和消失动画
        window!!.animationStyle = R.style.mypopwindow_anim_style
        // 在底部显示
        window!!.showAtLocation(
            context.findViewById(R.id.ll_logout),
            Gravity.BOTTOM, 0, 0
        )
        //popWindow消失监听方法
        window!!.setOnDismissListener {
            val lp = context.window.attributes
            lp.alpha = 1f //0.0-1.0
            context.window.attributes = lp
        }

    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_cancel -> {//取消按钮
                if (window != null) {
                    window!!.dismiss()
                }
            }
            R.id.ll_upload_img -> {//从相册选择
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                //              intent.setType("text/plain");
                intent.type = "image/*"
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                try {
                    (context as Activity).startActivityForResult(
                        Intent.createChooser(intent, "选择上传的照片"),
                        ConstantString.ALBUM_SELECT_CODE
                    )
                    window!!.dismiss()
                } catch (ex: android.content.ActivityNotFoundException) {
                    ToastUtil.showToast("请先安装文件浏览器！")
                }

            }
            R.id.ll_upload_camaro -> {//打开相机
                //申请运行时权限
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        context as Activity,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        0x113
                    )
                }
                val filePath =
                    Environment.getExternalStorageDirectory().toString() + "/HrbDownload/" + System.currentTimeMillis() + ".jpg"
                val outputFile = File(filePath)
                Log.i("123", "001: ")
                if (!outputFile.parentFile.exists()) {
                    Log.i("123", "00123: ")
                    outputFile.parentFile.mkdir()
                }
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    val contentUri = FileProvider.getUriForFile(context, "com.wisdom.project.myprovider", outputFile)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
                } else {
                    val photoUri = Uri.fromFile(outputFile) // 传递路径
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)// 更改系统默认存储路径
                }
                ConstantString.PIC_LOCATE = filePath
                (context as Activity).startActivityForResult(intent, REQUEST_CAMERA)

                window!!.dismiss()
            }
            R.id.ll_upload_file -> {//选择文件
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "text/plain"
                //                intent.setType("img/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                try {
                    (context as Activity).startActivityForResult(
                        Intent.createChooser(intent, "选择上传的文件"),
                        ConstantString.FILE_SELECT_CODE
                    )
                    window!!.dismiss()
                } catch (ex: android.content.ActivityNotFoundException) {
                    ToastUtil.showToast("请先安装文件浏览器！")
                }

            }
        }
    }

    companion object {
        private var window: PopupWindow? = null
    }


}
