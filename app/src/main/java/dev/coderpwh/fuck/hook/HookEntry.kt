package dev.coderpwh.fuck.hook

import android.hardware.SensorManager
import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.configs
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit

@InjectYukiHookWithXposed(modulePackageName = "dev.coderpwh.fuck.hook")
object HookEntry:IYukiHookXposedInit {

    override fun onInit() = configs {
        isDebug = false
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
    }
}