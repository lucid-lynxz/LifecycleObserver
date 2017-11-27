package org.lynxz.lifecyclerobserver.base

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.base_activity_launchmode.*
import org.lynxz.lifecyclerobserver.R
import org.lynxz.lifecyclerobserver.activity.SingleInstanceActivity
import org.lynxz.lifecyclerobserver.activity.SingleTaskActivity
import org.lynxz.lifecyclerobserver.activity.SingleTopActivity
import org.lynxz.lifecyclerobserver.activity.StandardActivity


/**
 * Created by lynxz on 26/10/2017.
 * 用于测试不同launchMode
 *
 * [flag介绍](http://www.jianshu.com/p/7f1c9fac2af2)
 */
abstract class BaseLaunchModeActivity : BaseActivity() {
    private var startFromApplication = false
    private var mFlagSelected = -1
    private val mItems = listOf("none flag",
            "FLAG_ACTIVITY_BROUGHT_TO_FRONT",
            "FLAG_ACTIVITY_NEW_TASK",
            "FLAG_ACTIVITY_CLEAR_TASK",
            "FLAG_ACTIVITY_NO_ANIMATION",
            "FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS",
            "FLAG_ACTIVITY_NO_HISTORY",
            "FLAG_ACTIVITY_FORWARD_RESULT",
            "FLAG_ACTIVITY_SINGLE_TOP",
            "FLAG_ACTIVITY_CLEAR_TOP")

    private val mFlagItems = listOf(-1,
            Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT,
            Intent.FLAG_ACTIVITY_NEW_TASK,
            Intent.FLAG_ACTIVITY_CLEAR_TASK,
            Intent.FLAG_ACTIVITY_NO_ANIMATION,
            Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS,
            Intent.FLAG_ACTIVITY_NO_HISTORY,
            Intent.FLAG_ACTIVITY_FORWARD_RESULT,
            Intent.FLAG_ACTIVITY_SINGLE_TOP,
            Intent.FLAG_ACTIVITY_CLEAR_TOP
    )
    private val mAdapter: ArrayAdapter<String> by lazy {
        ArrayAdapter(this, android.R.layout.simple_spinner_item, mItems)
    }

    override fun getLayoutRes() = R.layout.base_activity_launchmode

    override fun afterCreate() {

        sr_flag.let {
            it.adapter = mAdapter
            it.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    mFlagSelected = mFlagItems[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }

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
                if (mFlagSelected >= 0) {
                    addFlags(mFlagSelected)
                }
            })
        } else {
            startActivity(Intent(this@BaseLaunchModeActivity, targetAct).apply {
                if (mFlagSelected >= 0) {
                    addFlags(mFlagSelected)
                }
            })
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