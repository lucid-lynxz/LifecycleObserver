package org.lynxz.lifecyclerobserver.base

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.base_activity_launchmode.*
import org.lynxz.lifecyclerobserver.Logger
import org.lynxz.lifecyclerobserver.R
import org.lynxz.lifecyclerobserver.activity.*
import org.lynxz.lifecyclerobserver.showToast


/**
 * Created by lynxz on 26/10/2017.
 * 用于测试不同launchMode
 *
 * [flag介绍](http://www.jianshu.com/p/7f1c9fac2af2)
 *
 * 注意 Intent.FLAG_ACTIVITY_NEW_TASK 与 android:taskAffinity 的结合使用
 */
abstract class BaseLaunchModeActivity : BaseActivity() {
    private var startFromApplication = false
    private var startForResult = false

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

        title = this::class.java.simpleName

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

        cb_from_app.setOnCheckedChangeListener { _, isChecked ->
            startFromApplication = isChecked
            if (isChecked) {
                cb_start_for_result.isChecked = false
                cb_start_for_result.isEnabled = false
            } else {
                cb_start_for_result.isEnabled = true
            }
        }

        cb_start_for_result.setOnCheckedChangeListener { _, isChecked ->
            startForResult = isChecked
        }

        // standard
        tv_standard.setOnClickListener {
            startActivityCustomer(StandardAActivity::class.java)
        }

        tv_standard_b.setOnClickListener {
            startActivityCustomer(StandardBActivity::class.java)
        }

        // single top
        tv_single_top.setOnClickListener {
            startActivityCustomer(SingleTopActivity::class.java)
        }

        // single task
        tv_single_task.setOnClickListener {
            startActivityCustomer(SingleTaskAActivity::class.java)
        }
        tv_single_task_b.setOnClickListener {
            startActivityCustomer(SingleTaskBActivity::class.java)
        }

        // 带指定 taskAffinity 的 single task 页面
        tv_single_task_c.setOnClickListener {
            startActivityCustomer(SingleTaskCActivity::class.java)
        }
        tv_single_task_d.setOnClickListener {
            startActivityCustomer(SingleTaskDActivity::class.java)
        }

        // single instance
        tv_singe_instance.setOnClickListener {
            startActivityCustomer(SingleInstanceAActivity::class.java)
        }
        tv_singe_instance_b.setOnClickListener {
            startActivityCustomer(SingleInstanceBActivity::class.java)
        }

        tv_show_dialog.setOnClickListener {
            showDialog()
        }

        // 在页面finish的时候才有效, 返回给前一个页面
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra("activity", this@BaseLaunchModeActivity::class.java.simpleName)
        })
    }

    private fun startActivityCustomer(targetAct: Class<out BaseActivity>) {
        val requestCode = if (startForResult) 100 else -1
        if (startFromApplication) {
            applicationContext.startActivity(Intent(applicationContext, targetAct).apply {
                // 由于非 Activity 的 Context 没有所谓的任务栈,因此需要创建一个新的
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                if (mFlagSelected >= 0) {
                    addFlags(mFlagSelected)
                }
            })
        } else {
            startActivityForResult(Intent(this@BaseLaunchModeActivity, targetAct).apply {
                if (mFlagSelected >= 0) {
                    addFlags(mFlagSelected)
                    if (mFlagSelected == Intent.FLAG_ACTIVITY_NEW_TASK) {

                    }
                }
            }, requestCode)

        }
        resetStatus()
    }

    /**
     * 恢复各可选项为初始状态
     * */
    private fun resetStatus() {
        cb_start_for_result.isChecked = false
        cb_from_app.isChecked = false
        sr_flag.setSelection(0)

    }

    private fun showDialog() {
        AlertDialog.Builder(this)
                .setTitle("测试")
                .setMessage("这是个dialog")
                .setCancelable(true)
                .setPositiveButton("确定") { dialog, _ -> dialog?.dismiss() }
                .create()
                .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Logger.d("$requestCode $resultCode $data")
        showToast("onActivityResult $resultCode")
    }
}