package org.lynxz.lifecyclerobserver.activity

import android.content.Intent
import org.lynxz.lifecyclerobserver.Logger
import org.lynxz.lifecyclerobserver.base.BaseLaunchModeActivity

class StandardActivity : BaseLaunchModeActivity(){
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Logger.d("onActivityResult stand $resultCode")
    }
}