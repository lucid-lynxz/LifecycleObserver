package org.lynxz.lifecyclerobserver.activity

import android.content.Intent
import org.lynxz.lifecyclerobserver.Logger
import org.lynxz.lifecyclerobserver.base.BaseLaunchModeActivity

// 带 taskAffinity 的 launchMode = "standard" 的 activity
// 需要在  Intent 中添加 flag = Intent.FLAG_ACTIVITY_NEW_TASK 才会生效
class StandardBActivity : BaseLaunchModeActivity() {
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Logger.d("onActivityResult stand $resultCode")
    }
}