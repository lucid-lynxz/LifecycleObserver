package org.lynxz.lifecyclerobserver

import android.content.Intent
import kotlinx.android.synthetic.main.activity_main.*
import org.lynxz.lifecyclerobserver.activity.StandardActivity
import org.lynxz.lifecyclerobserver.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun getLayoutRes() = R.layout.activity_main

    override fun afterCreate() {
        tv_launch_mode.setOnClickListener {
            startActivity(Intent(this@MainActivity, StandardActivity::class.java))
        }
    }
}
