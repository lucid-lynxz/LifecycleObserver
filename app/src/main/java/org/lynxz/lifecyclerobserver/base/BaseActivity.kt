package org.lynxz.lifecyclerobserver.base


import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

/**
 * Created by xqc on 2017/3/13.
 * Developer App
 */
abstract class BaseActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeSetContentView()
        setContentView(getLayoutRes())
        afterCreate()
    }

    /**
     * 若要在 [.onCreate] SetContentView() 之前做设置请重写本方法
     */
    protected fun beforeSetContentView() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    /**
     * 获取内容布局id
     */
    protected abstract fun getLayoutRes(): Int

    /**
     * 在onCreate()后执行初始化工作
     * 若需要绑定ButterKnife则在该方法中绑定
     */
    protected abstract fun afterCreate()

    fun <T : View> findView(id: Int): T {
        return findViewById<View>(id) as T
    }

    override fun onClick(v: View?) {
    }
}