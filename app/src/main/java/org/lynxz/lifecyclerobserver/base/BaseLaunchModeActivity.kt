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
    private var startFromApplication = false

    override fun getLayoutRes() = R.layout.base_activity_launchmode

    override fun afterCreate() {

        cb_from_app.setOnClickListener {
            startFromApplication = true
        }

        tv_standard.setOnClickListener {
            startActivityCustomer(StandardActivity::class.java)
        }
        tv_single_top.setOnClickListener {
            startActivityCustomer(SingleTopActivity::class.java)
        }
        tv_single_task.setOnClickListener {
            startActivityCustomer(SingleTaskActivity::class.java)
        }
        tv_singe_instance.setOnClickListener {
            startActivityCustomer(SingleInstanceActivity::class.java)
        }
        tv_show_dialog.setOnClickListener {
            showDialog()
        }
    }

    private fun startActivityCustomer(targetAct: Class<out BaseActivity>) {
        if (startFromApplication) {
            applicationContext.startActivity(Intent(applicationContext, targetAct).apply {
                // 由于非 Activity 的 Context 没有所谓的任务栈,因此需要创建一个新的
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        } else {
            startActivity(Intent(this@BaseLaunchModeActivity, targetAct))
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