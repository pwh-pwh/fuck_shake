package dev.coderpwh.fuck.hook

import android.app.Activity
import android.content.Intent
import android.hardware.SensorManager
import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.configs
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.log.loggerI
import com.highcapable.yukihookapi.hook.type.android.ActivityClass
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit

@InjectYukiHookWithXposed(modulePackageName = "dev.coderpwh.fuck.hook")
object HookEntry:IYukiHookXposedInit {

    override fun onInit() = configs {
        isDebug = false
        debugLog {
            tag = "[fuck_shake]"
        }
    }

    override fun onHook() = encase {
        SensorManager::class.java.hook {
            injectMember {
                method {
                    name = "registerListener"
                    paramCount = 3
                }
                replaceToTrue()
            }
        }
        ActivityClass.hook {
            injectMember {
                method {
                    name = "startActivity"
                }
                replaceUnit {
                    loggerI(msg = "startActivity invoke")
                    val intent = args(0).cast<Intent>()!!
                    val pName = intent.component?.packageName
                    loggerI(msg = "from packageName:$packageName to pName:${pName}")
                    if(pName == packageName) {
                        callOriginal()
                    }
                }
            }

            injectMember {
                method {
                    name = "startActivityForResult"
                }
                replaceUnit {
                    loggerI(msg = "startActivityForResult invoke")
                    val intent = args(0).cast<Intent>()!!
                    val pName = intent.component?.packageName
                    loggerI(msg = "from packageName:$packageName to pName:${pName}")
                    if(pName == packageName) {
                        callOriginal()
                    }
                }
            }
        }
    }
}