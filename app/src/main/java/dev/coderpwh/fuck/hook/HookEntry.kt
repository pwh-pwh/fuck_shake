package dev.coderpwh.fuck.hook

import android.app.Activity
import android.content.Intent
import android.hardware.SensorManager
import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.configs
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.log.loggerI
import com.highcapable.yukihookapi.hook.param.HookParam
import com.highcapable.yukihookapi.hook.type.android.ActivityClass
import com.highcapable.yukihookapi.hook.type.android.ContextWrapperClass
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
        ContextWrapperClass.hook {
            injectMember {
                method {
                    name = "startActivity"
                }
                replaceUnit {
                    hookStartAt("startActivity",packageName)
                }
            }

            injectMember {
                method {
                    name = "startActivityForResult"
                }
                replaceUnit {
                    hookStartAt("startActivityForResult",packageName)
                }
            }
        }
    }
}

fun HookParam.hookStartAt(funName:String,packageName:String) {
    loggerI(msg = "${funName} invoke")
    val intent = args(0).cast<Intent>()!!
    val pName = intent.component?.packageName
    loggerI(msg = "from packageName:$packageName to pName:${pName}")
    if(pName == packageName || packageName == "android") {
        callOriginal()
    }
}