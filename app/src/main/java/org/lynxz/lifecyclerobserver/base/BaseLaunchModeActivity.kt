package org.lynxz.lifecyclerobserver.base

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AlertDialog
import kotlinx.android.synthetic.main.base_activity_launchmode.*
import org.lynxz.lifecyclerobserver.R
import org.lynxz.lifecyclerobserver.activity.SingleInstanceActivity
import org.lynxz.lifecyclerobserver.activity.SingleTaskActivity
import org.lynxz.lifecyclerobserver.activity.SingleTopActivity
import org.lynxz.lifecyclerobserver.activity.StandardActivity


/**
 * Created by lynxz on 26/10/2017.
 * 用于测试不同launchMode
 */
abstract class BaseLaunchModeActivity : BaseActivity() {
    override fun getLayoutRes() = R.layout.base_activity_launchmode

    override fun afterCreate() {
        tv_standard.setOnClickListener {
            startActivity(Intent(this@BaseLaunchModeActivity, StandardActivity::class.java))
        }
        tv_single_top.setOnClickListener {
            startActivity(Intent(this@BaseLaunchModeActivity, SingleTopActivity::class.java))
        }
        tv_single_task.setOnClickListener {
            startActivity(Intent(this@BaseLaunchModeActivity, SingleTaskActivity::class.java))
        }
        tv_singe_instance.setOnClickListener {
            startActivity(Intent(this@BaseLaunchModeActivity, SingleInstanceActivity::class.java))
        }
        tv_show_dialog.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(this)
                .setTitle("测试")
                .setMessage("这是个dialog")
                .setCancelable(true)
                .setPositiveButton("确定", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog?.dismiss()
                    }
                })
                .create()
                .show()

    }
}