package org.lynxz.lifecyclerobserver.activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import org.lynxz.lifecyclerobserver.TaskTreeManager
import org.lynxz.lifecyclerobserver.showToast

/**
 * Created by lynxz on 01/11/2017.
 */
class ShadowActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
        startActivityForResult(intent, 0)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && Settings.canDrawOverlays(this)) {
            TaskTreeManager.instance.init(application)
        } else {
            showToast("权限被禁,无法绘制悬浮窗")
        }
        finish()
    }
}